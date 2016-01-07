.title	"04a_ifs.ic"

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
.int 3
str2:	.string "TTT"
.int 3
str3:	.string "TTF"
.int 3
str4:	.string "TFT"
.int 3
str5:	.string "TFF"
.int 3
str6:	.string "FTT"
.int 3
str7:	.string "FTF"
.int 3
str8:	.string "FFT"
.int 3
str9:	.string "FFF"

# text (code) section
.text

# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $104, %esp

mov $12, %eax	# Move 12,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_x
mov %eax, -100(%ebp)

mov $8, %eax	# Move 8,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var2_y
mov %eax, -104(%ebp)

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var3_res
mov %eax, -96(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R1
mov %eax, -4(%ebp)

mov -100(%ebp), %eax	# Move var1_x,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JL _true_label1

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

mov -100(%ebp), %eax	# Move var1_x,R2
mov %eax, -8(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Mul R3,R2
mov -12(%ebp), %ebx
mul %ebx
mov %eax, -8(%ebp)

mov -100(%ebp), %eax	# Move var1_x,R3
mov %eax, -12(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Add R4,R3
Add -16(%ebp), %eax
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JG _true_label3

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label3

_true_label3:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label3:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JNZ _false_label4

mov $3, %eax	# Move 3,R3
mov %eax, -12(%ebp)

mov -100(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare 0,R5
cmp $0, %eax

JNZ _end_label6

mov $str1, %eax	# Move str1,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label6:

mov -16(%ebp), %eax	# Mod R5,R4
mov -20(%ebp), %ebx
mov $0, %edx
div %ebx
mov %edx, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JZ _true_label5

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label5

_true_label5:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label5:

mov -12(%ebp), %eax	# Compare 1,R3
cmp $1, %eax

JNZ _false_label7

mov $str2, %eax	# Move str2,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move R4,var3_res
mov %eax, -96(%ebp)

JMP _end_label7

_false_label7:

mov $str3, %eax	# Move str3,R6
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Move R6,var3_res
mov %eax, -96(%ebp)

_end_label7:

JMP _end_label4

_false_label4:

mov $3, %eax	# Move 3,R8
mov %eax, -32(%ebp)

mov -100(%ebp), %eax	# Move var1_x,R9
mov %eax, -36(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R10
mov %eax, -40(%ebp)

mov -40(%ebp), %eax	# Compare 0,R10
cmp $0, %eax

JNZ _end_label9

mov $str1, %eax	# Move str1,R10
mov %eax, -40(%ebp)

# Library __print(R10),Rdummy
mov -40(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label9:

mov -36(%ebp), %eax	# Mod R10,R9
mov -40(%ebp), %ebx
mov $0, %edx
div %ebx
mov %edx, -36(%ebp)

mov -36(%ebp), %eax	# Compare R8,R9
cmp -32(%ebp), %eax

JZ _true_label8

mov $0, %eax	# Move 0,R8
mov %eax, -32(%ebp)

JMP _end_label8

_true_label8:

mov $1, %eax	# Move 1,R8
mov %eax, -32(%ebp)

_end_label8:

mov -32(%ebp), %eax	# Compare 1,R8
cmp $1, %eax

JNZ _false_label10

mov $str4, %eax	# Move str4,R9
mov %eax, -36(%ebp)

mov -36(%ebp), %eax	# Move R9,var3_res
mov %eax, -96(%ebp)

JMP _end_label10

_false_label10:

mov $str5, %eax	# Move str5,R11
mov %eax, -44(%ebp)

mov -44(%ebp), %eax	# Move R11,var3_res
mov %eax, -96(%ebp)

_end_label10:

_end_label4:

JMP _end_label2

_false_label2:

mov -100(%ebp), %eax	# Move var1_x,R13
mov %eax, -52(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R14
mov %eax, -56(%ebp)

mov -52(%ebp), %eax	# Mul R14,R13
mov -56(%ebp), %ebx
mul %ebx
mov %eax, -52(%ebp)

mov -100(%ebp), %eax	# Move var1_x,R14
mov %eax, -56(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R15
mov %eax, -60(%ebp)

mov -56(%ebp), %eax	# Add R15,R14
Add -60(%ebp), %eax
mov %eax, -56(%ebp)

mov -56(%ebp), %eax	# Compare R13,R14
cmp -52(%ebp), %eax

JG _true_label11

mov $0, %eax	# Move 0,R13
mov %eax, -52(%ebp)

JMP _end_label11

_true_label11:

mov $1, %eax	# Move 1,R13
mov %eax, -52(%ebp)

_end_label11:

mov -52(%ebp), %eax	# Compare 1,R13
cmp $1, %eax

JNZ _false_label12

mov $3, %eax	# Move 3,R14
mov %eax, -56(%ebp)

mov -100(%ebp), %eax	# Move var1_x,R15
mov %eax, -60(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R16
mov %eax, -64(%ebp)

mov -64(%ebp), %eax	# Compare 0,R16
cmp $0, %eax

JNZ _end_label14

mov $str1, %eax	# Move str1,R16
mov %eax, -64(%ebp)

# Library __print(R16),Rdummy
mov -64(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label14:

mov -60(%ebp), %eax	# Mod R16,R15
mov -64(%ebp), %ebx
mov $0, %edx
div %ebx
mov %edx, -60(%ebp)

mov -60(%ebp), %eax	# Compare R14,R15
cmp -56(%ebp), %eax

JZ _true_label13

mov $0, %eax	# Move 0,R14
mov %eax, -56(%ebp)

JMP _end_label13

_true_label13:

mov $1, %eax	# Move 1,R14
mov %eax, -56(%ebp)

_end_label13:

mov -56(%ebp), %eax	# Compare 1,R14
cmp $1, %eax

JNZ _false_label15

mov $str6, %eax	# Move str6,R15
mov %eax, -60(%ebp)

mov -60(%ebp), %eax	# Move R15,var3_res
mov %eax, -96(%ebp)

JMP _end_label15

_false_label15:

mov $str7, %eax	# Move str7,R17
mov %eax, -68(%ebp)

mov -68(%ebp), %eax	# Move R17,var3_res
mov %eax, -96(%ebp)

_end_label15:

JMP _end_label12

_false_label12:

mov $3, %eax	# Move 3,R19
mov %eax, -76(%ebp)

mov -100(%ebp), %eax	# Move var1_x,R20
mov %eax, -80(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R21
mov %eax, -84(%ebp)

mov -84(%ebp), %eax	# Compare 0,R21
cmp $0, %eax

JNZ _end_label17

mov $str1, %eax	# Move str1,R21
mov %eax, -84(%ebp)

# Library __print(R21),Rdummy
mov -84(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label17:

mov -80(%ebp), %eax	# Mod R21,R20
mov -84(%ebp), %ebx
mov $0, %edx
div %ebx
mov %edx, -80(%ebp)

mov -80(%ebp), %eax	# Compare R19,R20
cmp -76(%ebp), %eax

JZ _true_label16

mov $0, %eax	# Move 0,R19
mov %eax, -76(%ebp)

JMP _end_label16

_true_label16:

mov $1, %eax	# Move 1,R19
mov %eax, -76(%ebp)

_end_label16:

mov -76(%ebp), %eax	# Compare 1,R19
cmp $1, %eax

JNZ _false_label18

mov $str8, %eax	# Move str8,R20
mov %eax, -80(%ebp)

mov -80(%ebp), %eax	# Move R20,var3_res
mov %eax, -96(%ebp)

JMP _end_label18

_false_label18:

mov $str9, %eax	# Move str9,R22
mov %eax, -88(%ebp)

mov -88(%ebp), %eax	# Move R22,var3_res
mov %eax, -96(%ebp)

_end_label18:

_end_label12:

_end_label2:

mov -96(%ebp), %eax	# Move var3_res,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $3, %eax	# Move 3,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_x
mov %eax, -100(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R1
mov %eax, -4(%ebp)

mov -100(%ebp), %eax	# Move var1_x,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JL _true_label19

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label19

_true_label19:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label19:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _false_label20

mov -100(%ebp), %eax	# Move var1_x,R2
mov %eax, -8(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Mul R3,R2
mov -12(%ebp), %ebx
mul %ebx
mov %eax, -8(%ebp)

mov -100(%ebp), %eax	# Move var1_x,R3
mov %eax, -12(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Add R4,R3
Add -16(%ebp), %eax
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JG _true_label21

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label21

_true_label21:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label21:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JNZ _false_label22

mov $3, %eax	# Move 3,R3
mov %eax, -12(%ebp)

mov -100(%ebp), %eax	# Move var1_x,R4
mov %eax, -16(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare 0,R5
cmp $0, %eax

JNZ _end_label24

mov $str1, %eax	# Move str1,R5
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

JZ _true_label23

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label23

_true_label23:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label23:

mov -12(%ebp), %eax	# Compare 1,R3
cmp $1, %eax

JNZ _false_label25

mov $str2, %eax	# Move str2,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move R4,var3_res
mov %eax, -96(%ebp)

JMP _end_label25

_false_label25:

mov $str3, %eax	# Move str3,R6
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Move R6,var3_res
mov %eax, -96(%ebp)

_end_label25:

JMP _end_label22

_false_label22:

mov $3, %eax	# Move 3,R8
mov %eax, -32(%ebp)

mov -100(%ebp), %eax	# Move var1_x,R9
mov %eax, -36(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R10
mov %eax, -40(%ebp)

mov -40(%ebp), %eax	# Compare 0,R10
cmp $0, %eax

JNZ _end_label27

mov $str1, %eax	# Move str1,R10
mov %eax, -40(%ebp)

# Library __print(R10),Rdummy
mov -40(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label27:

mov -36(%ebp), %eax	# Mod R10,R9
mov -40(%ebp), %ebx
mov $0, %edx
div %ebx
mov %edx, -36(%ebp)

mov -36(%ebp), %eax	# Compare R8,R9
cmp -32(%ebp), %eax

JZ _true_label26

mov $0, %eax	# Move 0,R8
mov %eax, -32(%ebp)

JMP _end_label26

_true_label26:

mov $1, %eax	# Move 1,R8
mov %eax, -32(%ebp)

_end_label26:

mov -32(%ebp), %eax	# Compare 1,R8
cmp $1, %eax

JNZ _false_label28

mov $str4, %eax	# Move str4,R9
mov %eax, -36(%ebp)

mov -36(%ebp), %eax	# Move R9,var3_res
mov %eax, -96(%ebp)

JMP _end_label28

_false_label28:

mov $str5, %eax	# Move str5,R11
mov %eax, -44(%ebp)

mov -44(%ebp), %eax	# Move R11,var3_res
mov %eax, -96(%ebp)

_end_label28:

_end_label22:

JMP _end_label20

_false_label20:

mov -100(%ebp), %eax	# Move var1_x,R13
mov %eax, -52(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R14
mov %eax, -56(%ebp)

mov -52(%ebp), %eax	# Mul R14,R13
mov -56(%ebp), %ebx
mul %ebx
mov %eax, -52(%ebp)

mov -100(%ebp), %eax	# Move var1_x,R14
mov %eax, -56(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R15
mov %eax, -60(%ebp)

mov -56(%ebp), %eax	# Add R15,R14
Add -60(%ebp), %eax
mov %eax, -56(%ebp)

mov -56(%ebp), %eax	# Compare R13,R14
cmp -52(%ebp), %eax

JG _true_label29

mov $0, %eax	# Move 0,R13
mov %eax, -52(%ebp)

JMP _end_label29

_true_label29:

mov $1, %eax	# Move 1,R13
mov %eax, -52(%ebp)

_end_label29:

mov -52(%ebp), %eax	# Compare 1,R13
cmp $1, %eax

JNZ _false_label30

mov $3, %eax	# Move 3,R14
mov %eax, -56(%ebp)

mov -100(%ebp), %eax	# Move var1_x,R15
mov %eax, -60(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R16
mov %eax, -64(%ebp)

mov -64(%ebp), %eax	# Compare 0,R16
cmp $0, %eax

JNZ _end_label32

mov $str1, %eax	# Move str1,R16
mov %eax, -64(%ebp)

# Library __print(R16),Rdummy
mov -64(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label32:

mov -60(%ebp), %eax	# Mod R16,R15
mov -64(%ebp), %ebx
mov $0, %edx
div %ebx
mov %edx, -60(%ebp)

mov -60(%ebp), %eax	# Compare R14,R15
cmp -56(%ebp), %eax

JZ _true_label31

mov $0, %eax	# Move 0,R14
mov %eax, -56(%ebp)

JMP _end_label31

_true_label31:

mov $1, %eax	# Move 1,R14
mov %eax, -56(%ebp)

_end_label31:

mov -56(%ebp), %eax	# Compare 1,R14
cmp $1, %eax

JNZ _false_label33

mov $str6, %eax	# Move str6,R15
mov %eax, -60(%ebp)

mov -60(%ebp), %eax	# Move R15,var3_res
mov %eax, -96(%ebp)

JMP _end_label33

_false_label33:

mov $str7, %eax	# Move str7,R17
mov %eax, -68(%ebp)

mov -68(%ebp), %eax	# Move R17,var3_res
mov %eax, -96(%ebp)

_end_label33:

JMP _end_label30

_false_label30:

mov $3, %eax	# Move 3,R19
mov %eax, -76(%ebp)

mov -100(%ebp), %eax	# Move var1_x,R20
mov %eax, -80(%ebp)

mov -104(%ebp), %eax	# Move var2_y,R21
mov %eax, -84(%ebp)

mov -84(%ebp), %eax	# Compare 0,R21
cmp $0, %eax

JNZ _end_label35

mov $str1, %eax	# Move str1,R21
mov %eax, -84(%ebp)

# Library __print(R21),Rdummy
mov -84(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label35:

mov -80(%ebp), %eax	# Mod R21,R20
mov -84(%ebp), %ebx
mov $0, %edx
div %ebx
mov %edx, -80(%ebp)

mov -80(%ebp), %eax	# Compare R19,R20
cmp -76(%ebp), %eax

JZ _true_label34

mov $0, %eax	# Move 0,R19
mov %eax, -76(%ebp)

JMP _end_label34

_true_label34:

mov $1, %eax	# Move 1,R19
mov %eax, -76(%ebp)

_end_label34:

mov -76(%ebp), %eax	# Compare 1,R19
cmp $1, %eax

JNZ _false_label36

mov $str8, %eax	# Move str8,R20
mov %eax, -80(%ebp)

mov -80(%ebp), %eax	# Move R20,var3_res
mov %eax, -96(%ebp)

JMP _end_label36

_false_label36:

mov $str9, %eax	# Move str9,R22
mov %eax, -88(%ebp)

mov -88(%ebp), %eax	# Move R22,var3_res
mov %eax, -96(%ebp)

_end_label36:

_end_label30:

_end_label20:

mov -96(%ebp), %eax	# Move var3_res,R2
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

