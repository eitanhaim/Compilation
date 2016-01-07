.title	"03a_loops.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_IFS:	.long 

.int 1
str0:	.string " "
.int 0
str1:	.string ""
.int 1
str2:	.string "*"

# text (code) section
.text

# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $52, %esp

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_x
mov %eax, -48(%ebp)

mov $20, %eax	# Move 20,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var2_y
mov %eax, -52(%ebp)

_test_label1:

mov $11, %eax	# Move 11,R1
mov %eax, -4(%ebp)

mov -48(%ebp), %eax	# Move var1_x,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JL _true_label2

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label2

_true_label2:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label2:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label1

mov -48(%ebp), %eax	# Move var1_x,R3
mov %eax, -12(%ebp)

mov $2, %eax	# Move 2,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Mul R4,R3
mov -16(%ebp), %ebx
mul %ebx
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

mov -48(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov $1, %eax	# Move 1,R5
mov %eax, -20(%ebp)

mov -16(%ebp), %eax	# Add R5,R4
Add -20(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move R4,var1_x
mov %eax, -48(%ebp)

JMP _test_label1

_end_label1:

mov $str1, %eax	# Move str1,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_x
mov %eax, -48(%ebp)

_test_label3:

mov $5, %eax	# Move 5,R1
mov %eax, -4(%ebp)

mov -48(%ebp), %eax	# Move var1_x,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JLE _true_label4

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label4

_true_label4:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label4:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label3

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Move R2,var2_y
mov %eax, -52(%ebp)

_test_label5:

mov -48(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -52(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JLE _true_label6

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label6

_true_label6:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label6:

mov -16(%ebp), %eax	# Compare 1,R4
cmp $1, %eax

JNZ _end_label5

mov $str2, %eax	# Move str2,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

mov -52(%ebp), %eax	# Move var2_y,R6
mov %eax, -24(%ebp)

mov $1, %eax	# Move 1,R7
mov %eax, -28(%ebp)

mov -24(%ebp), %eax	# Add R7,R6
Add -28(%ebp), %eax
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Move R6,var2_y
mov %eax, -52(%ebp)

JMP _test_label5

_end_label5:

mov $str1, %eax	# Move str1,R9
mov %eax, -36(%ebp)

# Library __println(R9),Rdummy
mov -36(%ebp), %eax
push %eax
call __println
add $4, %esp

mov -48(%ebp), %eax	# Move var1_x,R9
mov %eax, -36(%ebp)

mov $1, %eax	# Move 1,R10
mov %eax, -40(%ebp)

mov -36(%ebp), %eax	# Add R10,R9
Add -40(%ebp), %eax
mov %eax, -36(%ebp)

mov -36(%ebp), %eax	# Move R9,var1_x
mov %eax, -48(%ebp)

JMP _test_label3

_end_label3:

_PROGRAM_END:

# Library __exit(0),Rdummy
mov $0, %eax
push %eax
call __exit
add $4, %esp

