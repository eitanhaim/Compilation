.title	"02a_boolean.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_BoolCheck:	.long 

.int 32
str0:	.string "Runtime error: Division by zero!"
.int 0
str1:	.string ""

# text (code) section
.text

# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $40, %esp

mov $4, %eax	# Move 4,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_x
mov %eax, -36(%ebp)

mov $23, %eax	# Move 23,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var2_y
mov %eax, -40(%ebp)

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JG _true_label10

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label10

_true_label10:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label10:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JZ _end_label9

mov $5, %eax	# Move 5,R2
mov %eax, -8(%ebp)

mov $3, %eax	# Move 3,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Sub R3,R2
Sub -12(%ebp), %eax
mov %eax, -8(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R3
mov %eax, -12(%ebp)

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Sub R3,R4
Sub -12(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move R4,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JL _true_label11

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label11

_true_label11:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label11:

mov $5, %eax	# Move 5,R3
mov %eax, -12(%ebp)

mov $3, %eax	# Move 3,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Sub R4,R3
Sub -16(%ebp), %eax
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov $0, %eax	# Move 0,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Sub R4,R5
Sub -16(%ebp), %eax
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Move R5,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JL _true_label12

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label12

_true_label12:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label12:

mov -12(%ebp), %eax	# Move R3,R1
mov %eax, -4(%ebp)

_end_label9:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JZ _end_label8

mov -36(%ebp), %eax	# Move var1_x,R2
mov %eax, -8(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R3
mov %eax, -12(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JNZ _end_label14

mov $str0, %eax	# Move str0,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label14:

mov -12(%ebp), %eax	# Mod R4,R3
mov -16(%ebp), %ebx
mov $0, %edx
div %ebx
mov %edx, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JNZ _true_label13

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label13

_true_label13:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label13:

mov -36(%ebp), %eax	# Move var1_x,R3
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare 0,R5
cmp $0, %eax

JNZ _end_label16

mov $str0, %eax	# Move str0,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label16:

mov -16(%ebp), %eax	# Mod R5,R4
mov -20(%ebp), %ebx
mov $0, %edx
div %ebx
mov %edx, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JNZ _true_label15

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label15

_true_label15:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label15:

mov -12(%ebp), %eax	# Move R3,R1
mov %eax, -4(%ebp)

_end_label8:

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JZ _end_label7

mov $3, %eax	# Move 3,R2
mov %eax, -8(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R3
mov %eax, -12(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JNZ _end_label20

mov $str0, %eax	# Move str0,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label20:

mov -12(%ebp), %eax	# Div R4,R3
mov -16(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JG _true_label19

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label19

_true_label19:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label19:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JZ _end_label18

mov $5, %eax	# Move 5,R3
mov %eax, -12(%ebp)

mov $3, %eax	# Move 3,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Sub R4,R3
Sub -16(%ebp), %eax
mov %eax, -12(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -16(%ebp), %eax	# Sub R5,R4
Sub -20(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JL _true_label21

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label21

_true_label21:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label21:

mov $5, %eax	# Move 5,R4
mov %eax, -16(%ebp)

mov $3, %eax	# Move 3,R5
mov %eax, -20(%ebp)

mov -16(%ebp), %eax	# Sub R5,R4
Sub -20(%ebp), %eax
mov %eax, -16(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R5
mov %eax, -20(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R6
mov %eax, -24(%ebp)

mov -20(%ebp), %eax	# Sub R6,R5
Sub -24(%ebp), %eax
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JL _true_label22

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label22

_true_label22:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label22:

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

_end_label18:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JZ _end_label17

mov -36(%ebp), %eax	# Move var1_x,R3
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare 0,R5
cmp $0, %eax

JNZ _end_label24

mov $str0, %eax	# Move str0,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label24:

mov -16(%ebp), %eax	# Mod R5,R4
mov -20(%ebp), %ebx
mov $0, %edx
div %ebx
mov %edx, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JNZ _true_label23

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label23

_true_label23:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label23:

mov -36(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R6
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Compare 0,R6
cmp $0, %eax

JNZ _end_label26

mov $str0, %eax	# Move str0,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label26:

mov -20(%ebp), %eax	# Mod R6,R5
mov -24(%ebp), %ebx
mov $0, %edx
div %ebx
mov %edx, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JNZ _true_label25

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label25

_true_label25:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label25:

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

_end_label17:

mov -8(%ebp), %eax	# Move R2,R1
mov %eax, -4(%ebp)

_end_label7:

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JZ _end_label6

mov $3, %eax	# Move 3,R2
mov %eax, -8(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R3
mov %eax, -12(%ebp)

mov $2, %eax	# Move 2,R4
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -16(%ebp), %eax	# Mul R5,R4
mov -20(%ebp), %ebx
mul %ebx
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Add R4,R3
Add -16(%ebp), %eax
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JG _true_label29

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label29

_true_label29:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label29:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JZ _end_label28

mov $5, %eax	# Move 5,R3
mov %eax, -12(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -16(%ebp), %eax	# Sub R5,R4
Sub -20(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JL _true_label30

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label30

_true_label30:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label30:

mov $5, %eax	# Move 5,R4
mov %eax, -16(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R5
mov %eax, -20(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R6
mov %eax, -24(%ebp)

mov -20(%ebp), %eax	# Sub R6,R5
Sub -24(%ebp), %eax
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JL _true_label31

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label31

_true_label31:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label31:

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

_end_label28:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JZ _end_label27

mov -40(%ebp), %eax	# Move var2_y,R3
mov %eax, -12(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JZ _true_label32

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label32

_true_label32:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label32:

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JZ _true_label33

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label33

_true_label33:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label33:

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

_end_label27:

mov -8(%ebp), %eax	# Move R2,R1
mov %eax, -4(%ebp)

_end_label6:

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JZ _end_label5

mov -36(%ebp), %eax	# Move var1_x,R2
mov %eax, -8(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JZ _true_label36

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label36

_true_label36:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label36:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JZ _end_label35

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Sub R3,R4
Sub -12(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move R4,R3
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JL _true_label37

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label37

_true_label37:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label37:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

mov $0, %eax	# Move 0,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Sub R4,R5
Sub -16(%ebp), %eax
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Move R5,R4
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JL _true_label38

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label38

_true_label38:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label38:

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

_end_label35:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JZ _end_label34

mov -40(%ebp), %eax	# Move var2_y,R3
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JNZ _true_label39

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label39

_true_label39:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label39:

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JNZ _true_label40

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label40

_true_label40:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label40:

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

_end_label34:

mov -8(%ebp), %eax	# Move R2,R1
mov %eax, -4(%ebp)

_end_label5:

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JZ _end_label4

mov $3, %eax	# Move 3,R2
mov %eax, -8(%ebp)

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Sub R4,R3
Sub -16(%ebp), %eax
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JG _true_label43

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label43

_true_label43:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label43:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JZ _end_label42

mov $5, %eax	# Move 5,R3
mov %eax, -12(%ebp)

mov $8, %eax	# Move 8,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JNZ _end_label45

mov $str0, %eax	# Move str0,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label45:

mov -12(%ebp), %eax	# Div R4,R3
mov -16(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -12(%ebp)

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -16(%ebp), %eax	# Sub R5,R4
Sub -20(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JL _true_label44

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label44

_true_label44:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label44:

mov $5, %eax	# Move 5,R4
mov %eax, -16(%ebp)

mov $8, %eax	# Move 8,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare 0,R5
cmp $0, %eax

JNZ _end_label47

mov $str0, %eax	# Move str0,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label47:

mov -16(%ebp), %eax	# Div R5,R4
mov -20(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -16(%ebp)

mov $1, %eax	# Move 1,R5
mov %eax, -20(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R6
mov %eax, -24(%ebp)

mov -20(%ebp), %eax	# Sub R6,R5
Sub -24(%ebp), %eax
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JL _true_label46

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label46

_true_label46:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label46:

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

_end_label42:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JZ _end_label41

mov -40(%ebp), %eax	# Move var2_y,R3
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Sub R4,R3
Sub -16(%ebp), %eax
mov %eax, -12(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare 0,R5
cmp $0, %eax

JNZ _end_label49

mov $str0, %eax	# Move str0,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label49:

mov -16(%ebp), %eax	# Mod R5,R4
mov -20(%ebp), %ebx
mov $0, %edx
div %ebx
mov %edx, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JZ _true_label48

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label48

_true_label48:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label48:

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -16(%ebp), %eax	# Sub R5,R4
Sub -20(%ebp), %eax
mov %eax, -16(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R5
mov %eax, -20(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R6
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Compare 0,R6
cmp $0, %eax

JNZ _end_label51

mov $str0, %eax	# Move str0,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label51:

mov -20(%ebp), %eax	# Mod R6,R5
mov -24(%ebp), %ebx
mov $0, %edx
div %ebx
mov %edx, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JZ _true_label50

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label50

_true_label50:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label50:

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

_end_label41:

mov -8(%ebp), %eax	# Move R2,R1
mov %eax, -4(%ebp)

_end_label4:

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JZ _end_label3

mov $25, %eax	# Move 25,R2
mov %eax, -8(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R3
mov %eax, -12(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Mul R4,R3
mov -16(%ebp), %ebx
mul %ebx
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JG _true_label54

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label54

_true_label54:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label54:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JZ _end_label53

mov -36(%ebp), %eax	# Move var1_x,R3
mov %eax, -12(%ebp)

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Sub R3,R4
Sub -12(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move R4,R3
mov %eax, -12(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Mul R4,R3
mov -16(%ebp), %ebx
mul %ebx
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov $0, %eax	# Move 0,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Sub R4,R5
Sub -16(%ebp), %eax
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Move R5,R4
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -16(%ebp), %eax	# Mul R5,R4
mov -20(%ebp), %ebx
mul %ebx
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JL _true_label55

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label55

_true_label55:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label55:

mov -36(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov $0, %eax	# Move 0,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Sub R4,R5
Sub -16(%ebp), %eax
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Move R5,R4
mov %eax, -16(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R5
mov %eax, -20(%ebp)

mov -16(%ebp), %eax	# Mul R5,R4
mov -20(%ebp), %ebx
mul %ebx
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov $0, %eax	# Move 0,R6
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Sub R5,R6
Sub -20(%ebp), %eax
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Move R6,R5
mov %eax, -20(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R6
mov %eax, -24(%ebp)

mov -20(%ebp), %eax	# Mul R6,R5
mov -24(%ebp), %ebx
mul %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JL _true_label56

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label56

_true_label56:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label56:

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

_end_label53:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JZ _end_label52

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare 0,R5
cmp $0, %eax

JNZ _end_label58

mov $str0, %eax	# Move str0,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label58:

mov -16(%ebp), %eax	# Div R5,R4
mov -20(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JNZ _true_label57

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label57

_true_label57:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label57:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R6
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Compare 0,R6
cmp $0, %eax

JNZ _end_label60

mov $str0, %eax	# Move str0,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label60:

mov -20(%ebp), %eax	# Div R6,R5
mov -24(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JNZ _true_label59

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label59

_true_label59:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label59:

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

_end_label52:

mov -8(%ebp), %eax	# Move R2,R1
mov %eax, -4(%ebp)

_end_label3:

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JZ _end_label2

mov $2, %eax	# Move 2,R2
mov %eax, -8(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R3
mov %eax, -12(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JNZ _end_label64

mov $str0, %eax	# Move str0,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label64:

mov -12(%ebp), %eax	# Div R4,R3
mov -16(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -12(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JNZ _end_label65

mov $str0, %eax	# Move str0,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label65:

mov -12(%ebp), %eax	# Div R4,R3
mov -16(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JG _true_label63

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label63

_true_label63:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label63:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JZ _end_label62

mov $3, %eax	# Move 3,R3
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare 0,R5
cmp $0, %eax

JNZ _end_label67

mov $str0, %eax	# Move str0,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label67:

mov -16(%ebp), %eax	# Div R5,R4
mov -20(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -16(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare 0,R5
cmp $0, %eax

JNZ _end_label68

mov $str0, %eax	# Move str0,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label68:

mov -16(%ebp), %eax	# Div R5,R4
mov -20(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JL _true_label66

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label66

_true_label66:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label66:

mov $3, %eax	# Move 3,R4
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R6
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Compare 0,R6
cmp $0, %eax

JNZ _end_label70

mov $str0, %eax	# Move str0,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label70:

mov -20(%ebp), %eax	# Div R6,R5
mov -24(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R6
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Compare 0,R6
cmp $0, %eax

JNZ _end_label71

mov $str0, %eax	# Move str0,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label71:

mov -20(%ebp), %eax	# Div R6,R5
mov -24(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JL _true_label69

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label69

_true_label69:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label69:

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

_end_label62:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JZ _end_label61

mov -36(%ebp), %eax	# Move var1_x,R3
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R5
mov %eax, -20(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R6
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Compare 0,R6
cmp $0, %eax

JNZ _end_label73

mov $str0, %eax	# Move str0,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label73:

mov -20(%ebp), %eax	# Div R6,R5
mov -24(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare 0,R5
cmp $0, %eax

JNZ _end_label74

mov $str0, %eax	# Move str0,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label74:

mov -16(%ebp), %eax	# Div R5,R4
mov -20(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JL _true_label72

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label72

_true_label72:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label72:

mov -36(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R6
mov %eax, -24(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R7
mov %eax, -28(%ebp)

mov -28(%ebp), %eax	# Compare 0,R7
cmp $0, %eax

JNZ _end_label76

mov $str0, %eax	# Move str0,R7
mov %eax, -28(%ebp)

# Library __print(R7),Rdummy
mov -28(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label76:

mov -24(%ebp), %eax	# Div R7,R6
mov -28(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Compare 0,R6
cmp $0, %eax

JNZ _end_label77

mov $str0, %eax	# Move str0,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label77:

mov -20(%ebp), %eax	# Div R6,R5
mov -24(%ebp), %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JL _true_label75

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label75

_true_label75:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label75:

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

_end_label61:

mov -8(%ebp), %eax	# Move R2,R1
mov %eax, -4(%ebp)

_end_label2:

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JZ _end_label1

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JZ _true_label80

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label80

_true_label80:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label80:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JZ _end_label79

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JZ _true_label81

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label81

_true_label81:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label81:

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JZ _true_label82

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label82

_true_label82:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label82:

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

_end_label79:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JZ _end_label78

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R5
mov %eax, -20(%ebp)

mov -16(%ebp), %eax	# Sub R5,R4
Sub -20(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JG _true_label83

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label83

_true_label83:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label83:

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R6
mov %eax, -24(%ebp)

mov -20(%ebp), %eax	# Sub R6,R5
Sub -24(%ebp), %eax
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R4,R5
cmp -16(%ebp), %eax

JG _true_label84

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

JMP _end_label84

_true_label84:

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

_end_label84:

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

_end_label78:

mov -8(%ebp), %eax	# Move R2,R1
mov %eax, -4(%ebp)

_end_label1:

mov -4(%ebp), %eax	# Move R1,var3_p
mov %eax, -32(%ebp)

mov -32(%ebp), %eax	# Move var3_p,R2
mov %eax, -8(%ebp)

# Library __printb(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printb
add $4, %esp

mov $str1, %eax	# Move str1,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $3, %eax	# Move 3,R1
mov %eax, -4(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JL _true_label86

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label86

_true_label86:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label86:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JZ _end_label85

mov $24, %eax	# Move 24,R2
mov %eax, -8(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JG _true_label87

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label87

_true_label87:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label87:

mov $24, %eax	# Move 24,R3
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JG _true_label88

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label88

_true_label88:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label88:

mov -12(%ebp), %eax	# Move R3,R1
mov %eax, -4(%ebp)

_end_label85:

mov -4(%ebp), %eax	# Move R1,var3_p
mov %eax, -32(%ebp)

mov -32(%ebp), %eax	# Move var3_p,R2
mov %eax, -8(%ebp)

# Library __printb(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printb
add $4, %esp

mov $str1, %eax	# Move str1,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $3, %eax	# Move 3,R1
mov %eax, -4(%ebp)

mov -36(%ebp), %eax	# Move var1_x,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JG _true_label90

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label90

_true_label90:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label90:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JZ _end_label89

mov $24, %eax	# Move 24,R2
mov %eax, -8(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JG _true_label91

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label91

_true_label91:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label91:

mov $24, %eax	# Move 24,R3
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JG _true_label92

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label92

_true_label92:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label92:

mov -12(%ebp), %eax	# Move R3,R1
mov %eax, -4(%ebp)

_end_label89:

mov -4(%ebp), %eax	# Move R1,var3_p
mov %eax, -32(%ebp)

mov -32(%ebp), %eax	# Move var3_p,R2
mov %eax, -8(%ebp)

# Library __printb(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printb
add $4, %esp

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

