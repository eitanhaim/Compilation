.title	"04b_shortcircuit.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_A:	.long 
_DV_Check:	.long 

.int 21
str0:	.string "Should not be printed"
.int 13
str1:	.string "short circuit"
.int 10
str2:	.string "This is ok"

# text (code) section
.text

# -------------------
.align 4
_DV_A_true_print:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $16, %esp

mov $str1, %eax	# Move str1,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_b
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move var1_b,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JZ _end_label1

# StaticCall _DV_A_true_print(),R2
call _DV_A_true_print
mov %eax, -8(%ebp)

# StaticCall _DV_A_true_print(),R3
call _DV_A_true_print
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Move R3,R1
mov %eax, -4(%ebp)

_end_label1:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label2

mov $str2, %eax	# Move str2,R3
mov %eax, -12(%ebp)

# Library __println(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __println
add $4, %esp

_end_label2:

_PROGRAM_END:

# Library __exit(0),Rdummy
mov $0, %eax
push %eax
call __exit
add $4, %esp

