.title	"08a_recursive.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_A:	.long 
_DV_Check:	.long 

.int 1
str0:	.string " "
.int 0
str1:	.string ""

# text (code) section
.text

# -------------------
.align 4
_DV_A_calc_print_factorial:
push %ebp	# prologue
mov %esp, %ebp
sub $44, %esp

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

mov 8(%ebp), %eax	# Move param1_n,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JLE _true_label1

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label1

_true_label1:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label1:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _false_label2

mov 8(%ebp), %eax	# Move param1_n,R3
mov %eax, -12(%ebp)

# Library __printi(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str0, %eax	# Move str0,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

mov 8(%ebp), %eax	# Move param1_n,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Return R4
mov %ebp, %esp	# epilogue
pop %ebp
ret

JMP _end_label2

_false_label2:

mov 8(%ebp), %eax	# Move param1_n,R6
mov %eax, -24(%ebp)

mov $1, %eax	# Move 1,R7
mov %eax, -28(%ebp)

mov -24(%ebp), %eax	# Sub R7,R6
Sub -28(%ebp), %eax
mov %eax, -24(%ebp)

# StaticCall _DV_A_calc_print_factorial(param1_n=R6),R5
mov -24(%ebp), %eax
push %eax
call _DV_A_calc_print_factorial
add $4, %esp
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Move R5,var1_a
mov %eax, -44(%ebp)

mov -44(%ebp), %eax	# Move var1_a,R6
mov %eax, -24(%ebp)

mov 8(%ebp), %eax	# Move param1_n,R7
mov %eax, -28(%ebp)

mov -24(%ebp), %eax	# Mul R7,R6
mov -28(%ebp), %ebx
mul %ebx
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Move R6,var1_a
mov %eax, -44(%ebp)

mov -44(%ebp), %eax	# Move var1_a,R9
mov %eax, -36(%ebp)

# Library __printi(R9),Rdummy
mov -36(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str0, %eax	# Move str0,R10
mov %eax, -40(%ebp)

# Library __print(R10),Rdummy
mov -40(%ebp), %eax
push %eax
call __print
add $4, %esp

mov -44(%ebp), %eax	# Move var1_a,R10
mov %eax, -40(%ebp)

mov -40(%ebp), %eax	# Return R10
mov %ebp, %esp	# epilogue
pop %ebp
ret

_end_label2:


# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov $3, %eax	# Move 3,R2
mov %eax, -8(%ebp)

# StaticCall _DV_A_calc_print_factorial(param1_n=R2),R1
mov -8(%ebp), %eax
push %eax
call _DV_A_calc_print_factorial
add $4, %esp
mov %eax, -4(%ebp)

mov $str1, %eax	# Move str1,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $5, %eax	# Move 5,R2
mov %eax, -8(%ebp)

# StaticCall _DV_A_calc_print_factorial(param1_n=R2),R1
mov -8(%ebp), %eax
push %eax
call _DV_A_calc_print_factorial
add $4, %esp
mov %eax, -4(%ebp)

mov $str1, %eax	# Move str1,R2
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

