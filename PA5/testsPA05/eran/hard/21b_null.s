.title	"21b_null.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_MainClass:	.long 

.int 8
str0:	.string "null[2] "
.int 40
str1:	.string "Runtime error: Null pointer dereference!"
.int 41
str2:	.string "Runtime error: Array index out of bounds!"
.int 57
str3:	.string "Runtime error: Array allocation with negative array size!"
.int 0
str4:	.string ""
.int 34
str5:	.string "Should not print this or get here."

# text (code) section
.text

# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $24, %esp

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_a
mov %eax, -24(%ebp)

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov -24(%ebp), %eax	# Move var1_a,R2
mov %eax, -8(%ebp)

mov $2, %eax	# Move 2,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
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

mov -8(%ebp), %eax	# ArrayLength R2,R5
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R3,R5
cmp -12(%ebp), %eax

JG _end_label2

mov $str2, %eax	# Move str2,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label2:

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JGE _end_label3

mov $str3, %eax	# Move str3,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label3:

mov -8(%ebp), %eax	# MoveArray R2[R3],R4
mov -12(%ebp), %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -16(%ebp)

# Library __printi(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str4, %eax	# Move str4,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $str5, %eax	# Move str5,R2
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

