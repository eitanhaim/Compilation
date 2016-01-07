.title	"21d_null.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_A:	.long _DV_A_twice
_DV_MainClass:	.long 

.int 12
str0:	.string "null.method "
.int 40
str1:	.string "Runtime error: Null pointer dereference!"
.int 0
str2:	.string ""
.int 34
str3:	.string "Should not print this or get here."

# text (code) section
.text

# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $20, %esp

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_a
mov %eax, -20(%ebp)

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov -20(%ebp), %eax	# Move var1_a,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label1

mov $str1, %eax	# Move str1,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label1:

mov $2, %eax	# Move 2,R4
mov %eax, -16(%ebp)

mov -8(%ebp), %eax	# VirtualCall R2.0(param1_a=R4),R2
mov -16(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *0(%eax)
add $8, %esp
mov %eax, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str2, %eax	# Move str2,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $str3, %eax	# Move str3,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

_PROGRAM_END:

# Library __exit(0),Rdummy
mov $0, %eax
push %eax
call __exit
add $4, %esp


# -------------------
.align 4
_DV_A_twice:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov $2, %eax	# Move 2,R1
mov %eax, -4(%ebp)

mov 12(%ebp), %eax	# Move param1_a,R2
mov %eax, -8(%ebp)

mov -4(%ebp), %eax	# Mul R2,R1
mov -8(%ebp), %ebx
mul %ebx
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret

