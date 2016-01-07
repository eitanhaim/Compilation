.title	"04c_shortcircuit.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_A:	.long _DV_A_incField,_DV_A_print

.int 29
str0:	.string "This line will not be printed"
.int 40
str1:	.string "Runtime error: Null pointer dereference!"
.int 32
str2:	.string "Runtime error: Division by zero!"
.int 6
str3:	.string "res = "
.int 0
str4:	.string ""
.int 10
str5:	.string "a.field = "

# text (code) section
.text

# -------------------
.align 4
_DV_A_incField:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov 8(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

mov -4(%ebp), %eax	# Add R2,R1
Add -8(%ebp), %eax
mov %eax, -4(%ebp)

mov 8(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# MoveField R1,R2.1
mov $1, %ebx
mov -4(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov 8(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_A_print:
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
sub $32, %esp

# Library __allocateObject(8),R1
mov $8, %eax
push %eax
call __allocateObject
add $4, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField _DV_A,R1.0
mov $0, %ebx
mov $_DV_A, %ecx
mov %ecx, (%eax,%ebx,4)

mov -4(%ebp), %eax	# Move R1,var1_a
mov %eax, -24(%ebp)

mov $9, %eax	# Move 9,R1
mov %eax, -4(%ebp)

mov -24(%ebp), %eax	# Move var1_a,R2
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

mov -8(%ebp), %eax	# MoveField R1,R2.1
mov $1, %ebx
mov -4(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov $4, %eax	# Move 4,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var2_x
mov %eax, -32(%ebp)

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var3_res
mov %eax, -28(%ebp)

mov $3, %eax	# Move 3,R1
mov %eax, -4(%ebp)

mov -32(%ebp), %eax	# Move var2_x,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JG _true_label3

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label3

_true_label3:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label3:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JZ _end_label2

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

mov -32(%ebp), %eax	# Move var2_x,R3
mov %eax, -12(%ebp)

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JNZ _end_label5

mov $str2, %eax	# Move str2,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label5:

mov -12(%ebp), %eax	# Div R4,R3
mov -16(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JG _true_label4

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label4

_true_label4:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label4:

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

mov -32(%ebp), %eax	# Move var2_x,R4
mov %eax, -16(%ebp)

mov $0, %eax	# Move 0,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare 0,R5
cmp $0, %eax

JNZ _end_label7

mov $str2, %eax	# Move str2,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label7:

mov -16(%ebp), %eax	# Div R5,R4
mov -20(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JG _true_label6

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label6

_true_label6:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label6:

mov -12(%ebp), %eax	# Move R3,R1
mov %eax, -4(%ebp)

_end_label2:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label8

mov -28(%ebp), %eax	# Move var3_res,R2
mov %eax, -8(%ebp)

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Add R3,R2
Add -12(%ebp), %eax
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Move R2,var3_res
mov %eax, -28(%ebp)

_end_label8:

mov $3, %eax	# Move 3,R1
mov %eax, -4(%ebp)

mov -32(%ebp), %eax	# Move var2_x,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JL _true_label10

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label10

_true_label10:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label10:

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JZ _end_label9

mov $10, %eax	# Move 10,R2
mov %eax, -8(%ebp)

mov -24(%ebp), %eax	# Move var1_a,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label12

mov $str1, %eax	# Move str1,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label12:

mov -12(%ebp), %eax	# VirtualCall R3.0(),R3
push %eax
mov (%eax), %eax
call *0(%eax)
add $4, %esp
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JG _true_label11

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label11

_true_label11:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label11:

mov -8(%ebp), %eax	# Move R2,R1
mov %eax, -4(%ebp)

_end_label9:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label13

mov -28(%ebp), %eax	# Move var3_res,R2
mov %eax, -8(%ebp)

mov $10, %eax	# Move 10,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Add R3,R2
Add -12(%ebp), %eax
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Move R2,var3_res
mov %eax, -28(%ebp)

_end_label13:

mov $4, %eax	# Move 4,R1
mov %eax, -4(%ebp)

mov -32(%ebp), %eax	# Move var2_x,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JZ _true_label15

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label15

_true_label15:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label15:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JZ _end_label14

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

mov -24(%ebp), %eax	# Move var1_a,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label17

mov $str1, %eax	# Move str1,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label17:

mov -12(%ebp), %eax	# VirtualCall R3.1(),R3
push %eax
mov (%eax), %eax
call *4(%eax)
add $4, %esp
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JZ _true_label16

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label16

_true_label16:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label16:

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

mov -24(%ebp), %eax	# Move var1_a,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JNZ _end_label19

mov $str1, %eax	# Move str1,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label19:

mov -16(%ebp), %eax	# VirtualCall R4.1(),R4
push %eax
mov (%eax), %eax
call *4(%eax)
add $4, %esp
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JZ _true_label18

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label18

_true_label18:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label18:

mov -12(%ebp), %eax	# Move R3,R1
mov %eax, -4(%ebp)

_end_label14:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label20

mov -28(%ebp), %eax	# Move var3_res,R2
mov %eax, -8(%ebp)

mov $100, %eax	# Move 100,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Add R3,R2
Add -12(%ebp), %eax
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Move R2,var3_res
mov %eax, -28(%ebp)

_end_label20:

mov $str3, %eax	# Move str3,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

mov -28(%ebp), %eax	# Move var3_res,R2
mov %eax, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
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

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

mov -24(%ebp), %eax	# Move var1_a,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label21

mov $str1, %eax	# Move str1,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label21:

mov -8(%ebp), %eax	# MoveField R2.1,R2
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

_PROGRAM_END:

# Library __exit(0),Rdummy
mov $0, %eax
push %eax
call __exit
add $4, %esp

