.title	"02b_arith.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_IFS:	.long 

.int 0
str0:	.string ""
.int 32
str1:	.string "Runtime error: Division by zero!"

# text (code) section
.text

# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $28, %esp

mov $5, %eax	# Move 5,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_a
mov %eax, -20(%ebp)

mov $10, %eax	# Move 10,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var2_b
mov %eax, -24(%ebp)

mov $20, %eax	# Move 20,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var3_c
mov %eax, -28(%ebp)

mov -20(%ebp), %eax	# Move var1_a,R2
mov %eax, -8(%ebp)

mov -24(%ebp), %eax	# Move var2_b,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Add R3,R2
Add -12(%ebp), %eax
mov %eax, -8(%ebp)

mov -28(%ebp), %eax	# Move var3_c,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Add R3,R2
Add -12(%ebp), %eax
mov %eax, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov -20(%ebp), %eax	# Move var1_a,R2
mov %eax, -8(%ebp)

mov -24(%ebp), %eax	# Move var2_b,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Mul R3,R2
mov -12(%ebp), %ebx
mul %ebx
mov %eax, -8(%ebp)

mov -28(%ebp), %eax	# Move var3_c,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Add R3,R2
Add -12(%ebp), %eax
mov %eax, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov -24(%ebp), %eax	# Move var2_b,R2
mov %eax, -8(%ebp)

mov -20(%ebp), %eax	# Move var1_a,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label1

mov $str1, %eax	# Move str1,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label1:

mov -8(%ebp), %eax	# Div R3,R2
mov -12(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -8(%ebp)

mov -28(%ebp), %eax	# Move var3_c,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Mul R3,R2
mov -12(%ebp), %ebx
mul %ebx
mov %eax, -8(%ebp)

mov -28(%ebp), %eax	# Move var3_c,R3
mov %eax, -12(%ebp)

mov -28(%ebp), %eax	# Move var3_c,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Mul R4,R3
mov -16(%ebp), %ebx
mul %ebx
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Add R3,R2
Add -12(%ebp), %eax
mov %eax, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov -28(%ebp), %eax	# Move var3_c,R2
mov %eax, -8(%ebp)

mov -24(%ebp), %eax	# Move var2_b,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Sub R3,R2
Sub -12(%ebp), %eax
mov %eax, -8(%ebp)

mov -20(%ebp), %eax	# Move var1_a,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Sub R3,R2
Sub -12(%ebp), %eax
mov %eax, -8(%ebp)

mov $5, %eax	# Move 5,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Sub R3,R2
Sub -12(%ebp), %eax
mov %eax, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str0, %eax	# Move str0,R2
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

