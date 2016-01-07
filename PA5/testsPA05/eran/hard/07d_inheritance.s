.title	"07d_inheritance.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_A:	.long _DV_A_setX,_DV_A_setY,_DV_A_print
_DV_B:	.long _DV_A_setX,_DV_A_setY,_DV_B_print
_DV_MainClass:	.long 
_DV_C:	.long _DV_A_setX,_DV_A_setY,_DV_C_print,_DV_C_setZ

.int 40
str0:	.string "Runtime error: Null pointer dereference!"
.int 4
str1:	.string "A: ["
.int 2
str2:	.string ", "
.int 2
str3:	.string "] "
.int 0
str4:	.string ""
.int 14
str5:	.string "**************"
.int 6
str6:	.string "* B: ["
.int 3
str7:	.string "] *"
.int 18
str8:	.string "******************"
.int 6
str9:	.string "* C: ["

# text (code) section
.text

# -------------------
.align 4
_DV_A_setX:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov 12(%ebp), %eax	# Move param1_x1,R1
mov %eax, -4(%ebp)

mov 8(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# MoveField R1,R2.1
mov $1, %ebx
mov -4(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_A_setY:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov 12(%ebp), %eax	# Move param1_y1,R1
mov %eax, -4(%ebp)

mov 8(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label1

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label1:

mov -8(%ebp), %eax	# MoveField R1,R2.2
mov $2, %ebx
mov -4(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_A_print:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov $str1, %eax	# Move str1,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

mov 8(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# MoveField R2.1,R2
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str2, %eax	# Move str2,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

mov 8(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label2

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label2:

mov -8(%ebp), %eax	# MoveField R2.2,R2
mov $2, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str3, %eax	# Move str3,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

mov $str4, %eax	# Move str4,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_B_print:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov $str5, %eax	# Move str5,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $str6, %eax	# Move str6,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

mov 8(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# MoveField R2.1,R2
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str2, %eax	# Move str2,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

mov 8(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label3

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label3:

mov -8(%ebp), %eax	# MoveField R2.2,R2
mov $2, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str7, %eax	# Move str7,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
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

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_C_setZ:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov 12(%ebp), %eax	# Move param1_z,R1
mov %eax, -4(%ebp)

mov 8(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label4

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label4:

mov -8(%ebp), %eax	# MoveField R1,R2.3
mov $3, %ebx
mov -4(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_C_print:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov $str8, %eax	# Move str8,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $str9, %eax	# Move str9,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

mov 8(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# MoveField R2.1,R2
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str2, %eax	# Move str2,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

mov 8(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label5

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label5:

mov -8(%ebp), %eax	# MoveField R2.2,R2
mov $2, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str2, %eax	# Move str2,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

mov 8(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# MoveField R2.3,R2
mov $3, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str7, %eax	# Move str7,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

mov $str4, %eax	# Move str4,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $str8, %eax	# Move str8,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $28, %esp

# Library __allocateObject(12),R1
mov $12, %eax
push %eax
call __allocateObject
add $4, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField _DV_A,R1.0
mov $0, %ebx
mov $_DV_A, %ecx
mov %ecx, (%eax,%ebx,4)

mov -4(%ebp), %eax	# Move R1,var1_a
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Move var1_a,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label6

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label6:

mov $4, %eax	# Move 4,R3
mov %eax, -12(%ebp)

mov -4(%ebp), %eax	# VirtualCall R1.0(param1_x1=R3),Rdummy
mov -12(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *0(%eax)
add $8, %esp

mov -20(%ebp), %eax	# Move var1_a,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label7

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label7:

mov $8, %eax	# Move 8,R3
mov %eax, -12(%ebp)

mov -4(%ebp), %eax	# VirtualCall R1.1(param1_y1=R3),Rdummy
mov -12(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *4(%eax)
add $8, %esp

# Library __allocateObject(12),R1
mov $12, %eax
push %eax
call __allocateObject
add $4, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField _DV_B,R1.0
mov $0, %ebx
mov $_DV_B, %ecx
mov %ecx, (%eax,%ebx,4)

mov -4(%ebp), %eax	# Move R1,var2_b
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Move var2_b,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label8

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label8:

mov $2, %eax	# Move 2,R3
mov %eax, -12(%ebp)

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Sub R3,R4
Sub -12(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move R4,R3
mov %eax, -12(%ebp)

mov -4(%ebp), %eax	# VirtualCall R1.0(param1_x1=R3),Rdummy
mov -12(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *0(%eax)
add $8, %esp

mov -24(%ebp), %eax	# Move var2_b,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label9

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label9:

mov $5, %eax	# Move 5,R3
mov %eax, -12(%ebp)

mov -4(%ebp), %eax	# VirtualCall R1.1(param1_y1=R3),Rdummy
mov -12(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *4(%eax)
add $8, %esp

# Library __allocateObject(16),R1
mov $16, %eax
push %eax
call __allocateObject
add $4, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField _DV_C,R1.0
mov $0, %ebx
mov $_DV_C, %ecx
mov %ecx, (%eax,%ebx,4)

mov -4(%ebp), %eax	# Move R1,var3_c
mov %eax, -28(%ebp)

mov -28(%ebp), %eax	# Move var3_c,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label10

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label10:

mov $3, %eax	# Move 3,R3
mov %eax, -12(%ebp)

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Sub R3,R4
Sub -12(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move R4,R3
mov %eax, -12(%ebp)

mov -4(%ebp), %eax	# VirtualCall R1.0(param1_x1=R3),Rdummy
mov -12(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *0(%eax)
add $8, %esp

mov -28(%ebp), %eax	# Move var3_c,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label11

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label11:

mov $3, %eax	# Move 3,R3
mov %eax, -12(%ebp)

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Sub R3,R4
Sub -12(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move R4,R3
mov %eax, -12(%ebp)

mov -4(%ebp), %eax	# VirtualCall R1.1(param1_y1=R3),Rdummy
mov -12(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *4(%eax)
add $8, %esp

mov -20(%ebp), %eax	# Move var1_a,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label12

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label12:

mov -4(%ebp), %eax	# VirtualCall R1.2(),Rdummy
push %eax
mov (%eax), %eax
call *8(%eax)
add $4, %esp

mov -24(%ebp), %eax	# Move var2_b,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label13

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label13:

mov -4(%ebp), %eax	# VirtualCall R1.2(),Rdummy
push %eax
mov (%eax), %eax
call *8(%eax)
add $4, %esp

mov -28(%ebp), %eax	# Move var3_c,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label14

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label14:

mov -4(%ebp), %eax	# VirtualCall R1.2(),Rdummy
push %eax
mov (%eax), %eax
call *8(%eax)
add $4, %esp

_PROGRAM_END:

# Library __exit(0),Rdummy
mov $0, %eax
push %eax
call __exit
add $4, %esp

