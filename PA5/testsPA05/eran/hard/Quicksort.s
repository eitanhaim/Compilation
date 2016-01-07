.title	"Quicksort.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_Quicksort:	.long _DV_Quicksort_partition,_DV_Quicksort_quicksort,_DV_Quicksort_initArray,_DV_Quicksort_printArray

.int 40
str0:	.string "Runtime error: Null pointer dereference!"
.int 41
str1:	.string "Runtime error: Array index out of bounds!"
.int 57
str2:	.string "Runtime error: Array allocation with negative array size!"
.int 16
str3:	.string "Array elements: "
.int 1
str4:	.string " "
.int 1
str5:	.string "
"
.int 24
str6:	.string "Unspecified array length"
.int 20
str7:	.string "Invalid array length"

# text (code) section
.text

# -------------------
.align 4
_DV_Quicksort_partition:
push %ebp	# prologue
mov %esp, %ebp
sub $112, %esp

mov 8(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

mov 12(%ebp), %eax	# Move param1_low,R2
mov %eax, -8(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
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

mov -4(%ebp), %eax	# ArrayLength R1,R4
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R2,R4
cmp -8(%ebp), %eax

JG _end_label2

mov $str1, %eax	# Move str1,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label2:

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JGE _end_label3

mov $str2, %eax	# Move str2,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label3:

mov -4(%ebp), %eax	# MoveArray R1[R2],R3
mov -8(%ebp), %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -12(%ebp)

mov -12(%ebp), %eax	# Move R3,var1_pivot
mov %eax, -104(%ebp)

mov 12(%ebp), %eax	# Move param1_low,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var2_i
mov %eax, -108(%ebp)

mov 16(%ebp), %eax	# Move param2_high,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var3_j
mov %eax, -112(%ebp)

_test_label4:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label4

_test_label5:

mov -104(%ebp), %eax	# Move var1_pivot,R2
mov %eax, -8(%ebp)

mov 8(%ebp), %eax	# Move this,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# MoveField R3.1,R3
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -12(%ebp)

mov -108(%ebp), %eax	# Move var2_i,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label7

mov $str0, %eax	# Move str0,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label7:

mov -12(%ebp), %eax	# ArrayLength R3,R6
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Compare R4,R6
cmp -16(%ebp), %eax

JG _end_label8

mov $str1, %eax	# Move str1,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label8:

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JGE _end_label9

mov $str2, %eax	# Move str2,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label9:

mov -12(%ebp), %eax	# MoveArray R3[R4],R5
mov -16(%ebp), %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -20(%ebp)

mov -20(%ebp), %eax	# Compare R2,R5
cmp -8(%ebp), %eax

JL _true_label6

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label6

_true_label6:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label6:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JNZ _end_label5

mov -108(%ebp), %eax	# Move var2_i,R3
mov %eax, -12(%ebp)

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Add R4,R3
Add -16(%ebp), %eax
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Move R3,var2_i
mov %eax, -108(%ebp)

JMP _test_label5

_end_label5:

_test_label10:

mov -104(%ebp), %eax	# Move var1_pivot,R5
mov %eax, -20(%ebp)

mov 8(%ebp), %eax	# Move this,R6
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# MoveField R6.1,R6
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -24(%ebp)

mov -112(%ebp), %eax	# Move var3_j,R7
mov %eax, -28(%ebp)

mov -24(%ebp), %eax	# Compare 0,R6
cmp $0, %eax

JNZ _end_label12

mov $str0, %eax	# Move str0,R7
mov %eax, -28(%ebp)

# Library __print(R7),Rdummy
mov -28(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label12:

mov -24(%ebp), %eax	# ArrayLength R6,R9
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -36(%ebp)

mov -36(%ebp), %eax	# Compare R7,R9
cmp -28(%ebp), %eax

JG _end_label13

mov $str1, %eax	# Move str1,R9
mov %eax, -36(%ebp)

# Library __print(R9),Rdummy
mov -36(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label13:

mov -28(%ebp), %eax	# Compare 0,R7
cmp $0, %eax

JGE _end_label14

mov $str2, %eax	# Move str2,R7
mov %eax, -28(%ebp)

# Library __print(R7),Rdummy
mov -28(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label14:

mov -24(%ebp), %eax	# MoveArray R6[R7],R8
mov -28(%ebp), %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -32(%ebp)

mov -32(%ebp), %eax	# Compare R5,R8
cmp -20(%ebp), %eax

JG _true_label11

mov $0, %eax	# Move 0,R5
mov %eax, -20(%ebp)

JMP _end_label11

_true_label11:

mov $1, %eax	# Move 1,R5
mov %eax, -20(%ebp)

_end_label11:

mov -20(%ebp), %eax	# Compare 1,R5
cmp $1, %eax

JNZ _end_label10

mov -112(%ebp), %eax	# Move var3_j,R6
mov %eax, -24(%ebp)

mov $1, %eax	# Move 1,R7
mov %eax, -28(%ebp)

mov -24(%ebp), %eax	# Sub R7,R6
Sub -28(%ebp), %eax
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Move R6,var3_j
mov %eax, -112(%ebp)

JMP _test_label10

_end_label10:

mov -112(%ebp), %eax	# Move var3_j,R8
mov %eax, -32(%ebp)

mov -108(%ebp), %eax	# Move var2_i,R9
mov %eax, -36(%ebp)

mov -32(%ebp), %eax	# Compare R8,R8
cmp -32(%ebp), %eax

JGE _true_label15

mov $0, %eax	# Move 0,R8
mov %eax, -32(%ebp)

JMP _end_label15

_true_label15:

mov $1, %eax	# Move 1,R8
mov %eax, -32(%ebp)

_end_label15:

mov -32(%ebp), %eax	# Compare 1,R8
cmp $1, %eax

JNZ _end_label16

JMP _end_label4

_end_label16:

mov 8(%ebp), %eax	# Move this,R9
mov %eax, -36(%ebp)

mov -36(%ebp), %eax	# MoveField R9.1,R9
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -36(%ebp)

mov -108(%ebp), %eax	# Move var2_i,R10
mov %eax, -40(%ebp)

mov -36(%ebp), %eax	# Compare 0,R9
cmp $0, %eax

JNZ _end_label17

mov $str0, %eax	# Move str0,R10
mov %eax, -40(%ebp)

# Library __print(R10),Rdummy
mov -40(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label17:

mov -36(%ebp), %eax	# ArrayLength R9,R12
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -48(%ebp)

mov -48(%ebp), %eax	# Compare R10,R12
cmp -40(%ebp), %eax

JG _end_label18

mov $str1, %eax	# Move str1,R12
mov %eax, -48(%ebp)

# Library __print(R12),Rdummy
mov -48(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label18:

mov -40(%ebp), %eax	# Compare 0,R10
cmp $0, %eax

JGE _end_label19

mov $str2, %eax	# Move str2,R10
mov %eax, -40(%ebp)

# Library __print(R10),Rdummy
mov -40(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label19:

mov -36(%ebp), %eax	# MoveArray R9[R10],R11
mov -40(%ebp), %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -44(%ebp)

mov -44(%ebp), %eax	# Move R11,var4_tmp
mov %eax, -100(%ebp)

mov 8(%ebp), %eax	# Move this,R13
mov %eax, -52(%ebp)

mov -52(%ebp), %eax	# MoveField R13.1,R13
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -52(%ebp)

mov -112(%ebp), %eax	# Move var3_j,R14
mov %eax, -56(%ebp)

mov -52(%ebp), %eax	# Compare 0,R13
cmp $0, %eax

JNZ _end_label20

mov $str0, %eax	# Move str0,R14
mov %eax, -56(%ebp)

# Library __print(R14),Rdummy
mov -56(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label20:

mov -52(%ebp), %eax	# ArrayLength R13,R16
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -64(%ebp)

mov -64(%ebp), %eax	# Compare R14,R16
cmp -56(%ebp), %eax

JG _end_label21

mov $str1, %eax	# Move str1,R16
mov %eax, -64(%ebp)

# Library __print(R16),Rdummy
mov -64(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label21:

mov -56(%ebp), %eax	# Compare 0,R14
cmp $0, %eax

JGE _end_label22

mov $str2, %eax	# Move str2,R14
mov %eax, -56(%ebp)

# Library __print(R14),Rdummy
mov -56(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label22:

mov -52(%ebp), %eax	# MoveArray R13[R14],R15
mov -56(%ebp), %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -60(%ebp)

mov 8(%ebp), %eax	# Move this,R16
mov %eax, -64(%ebp)

mov -64(%ebp), %eax	# MoveField R16.1,R16
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -64(%ebp)

mov -108(%ebp), %eax	# Move var2_i,R17
mov %eax, -68(%ebp)

mov -64(%ebp), %eax	# Compare 0,R16
cmp $0, %eax

JNZ _end_label23

mov $str0, %eax	# Move str0,R17
mov %eax, -68(%ebp)

# Library __print(R17),Rdummy
mov -68(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label23:

mov -64(%ebp), %eax	# ArrayLength R16,R19
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -76(%ebp)

mov -76(%ebp), %eax	# Compare R17,R19
cmp -68(%ebp), %eax

JG _end_label24

mov $str1, %eax	# Move str1,R19
mov %eax, -76(%ebp)

# Library __print(R19),Rdummy
mov -76(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label24:

mov -68(%ebp), %eax	# Compare 0,R17
cmp $0, %eax

JGE _end_label25

mov $str2, %eax	# Move str2,R17
mov %eax, -68(%ebp)

# Library __print(R17),Rdummy
mov -68(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label25:

mov -64(%ebp), %eax	# MoveArray R15,R16[R17]
mov -68(%ebp), %ebx
mov -60(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov -100(%ebp), %eax	# Move var4_tmp,R18
mov %eax, -72(%ebp)

mov 8(%ebp), %eax	# Move this,R19
mov %eax, -76(%ebp)

mov -76(%ebp), %eax	# MoveField R19.1,R19
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -76(%ebp)

mov -112(%ebp), %eax	# Move var3_j,R20
mov %eax, -80(%ebp)

mov -76(%ebp), %eax	# Compare 0,R19
cmp $0, %eax

JNZ _end_label26

mov $str0, %eax	# Move str0,R20
mov %eax, -80(%ebp)

# Library __print(R20),Rdummy
mov -80(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label26:

mov -76(%ebp), %eax	# ArrayLength R19,R22
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -88(%ebp)

mov -88(%ebp), %eax	# Compare R20,R22
cmp -80(%ebp), %eax

JG _end_label27

mov $str1, %eax	# Move str1,R22
mov %eax, -88(%ebp)

# Library __print(R22),Rdummy
mov -88(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label27:

mov -80(%ebp), %eax	# Compare 0,R20
cmp $0, %eax

JGE _end_label28

mov $str2, %eax	# Move str2,R20
mov %eax, -80(%ebp)

# Library __print(R20),Rdummy
mov -80(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label28:

mov -76(%ebp), %eax	# MoveArray R18,R19[R20]
mov -80(%ebp), %ebx
mov -72(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov -108(%ebp), %eax	# Move var2_i,R21
mov %eax, -84(%ebp)

mov $1, %eax	# Move 1,R22
mov %eax, -88(%ebp)

mov -84(%ebp), %eax	# Add R22,R21
Add -88(%ebp), %eax
mov %eax, -84(%ebp)

mov -84(%ebp), %eax	# Move R21,var2_i
mov %eax, -108(%ebp)

mov -112(%ebp), %eax	# Move var3_j,R23
mov %eax, -92(%ebp)

mov $1, %eax	# Move 1,R24
mov %eax, -96(%ebp)

mov -92(%ebp), %eax	# Sub R24,R23
Sub -96(%ebp), %eax
mov %eax, -92(%ebp)

mov -92(%ebp), %eax	# Move R23,var3_j
mov %eax, -112(%ebp)

JMP _test_label4

_end_label4:

mov -112(%ebp), %eax	# Move var3_j,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_Quicksort_quicksort:
push %ebp	# prologue
mov %esp, %ebp
sub $32, %esp

mov 16(%ebp), %eax	# Move param2_high,R1
mov %eax, -4(%ebp)

mov 12(%ebp), %eax	# Move param1_low,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JL _true_label29

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label29

_true_label29:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label29:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label30

mov 8(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label31

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label31:

mov 12(%ebp), %eax	# Move param1_low,R4
mov %eax, -16(%ebp)

mov 16(%ebp), %eax	# Move param2_high,R5
mov %eax, -20(%ebp)

mov -8(%ebp), %eax	# VirtualCall R2.0(param1_low=R4, param2_high=R5),R2
mov -20(%ebp), %ebx
push %ebx
mov -16(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *0(%eax)
add $12, %esp
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Move R2,var1_mid
mov %eax, -32(%ebp)

mov 8(%ebp), %eax	# Move this,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label32

mov $str0, %eax	# Move str0,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label32:

mov 12(%ebp), %eax	# Move param1_low,R5
mov %eax, -20(%ebp)

mov -32(%ebp), %eax	# Move var1_mid,R6
mov %eax, -24(%ebp)

mov -12(%ebp), %eax	# VirtualCall R3.1(param1_low=R5, param2_high=R6),Rdummy
mov -24(%ebp), %ebx
push %ebx
mov -20(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *4(%eax)
add $12, %esp

mov 8(%ebp), %eax	# Move this,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JNZ _end_label33

mov $str0, %eax	# Move str0,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label33:

mov -32(%ebp), %eax	# Move var1_mid,R6
mov %eax, -24(%ebp)

mov $1, %eax	# Move 1,R7
mov %eax, -28(%ebp)

mov -24(%ebp), %eax	# Add R7,R6
Add -28(%ebp), %eax
mov %eax, -24(%ebp)

mov 16(%ebp), %eax	# Move param2_high,R7
mov %eax, -28(%ebp)

mov -16(%ebp), %eax	# VirtualCall R4.1(param1_low=R6, param2_high=R7),Rdummy
mov -28(%ebp), %ebx
push %ebx
mov -24(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *4(%eax)
add $12, %esp

_end_label30:

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_Quicksort_initArray:
push %ebp	# prologue
mov %esp, %ebp
sub $28, %esp

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_i
mov %eax, -28(%ebp)

_test_label34:

mov 8(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label36

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label36:

mov -4(%ebp), %eax	# ArrayLength R1,R1
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -4(%ebp)

mov -28(%ebp), %eax	# Move var1_i,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JL _true_label35

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label35

_true_label35:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label35:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label34

mov 8(%ebp), %eax	# Move this,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# MoveField R3.1,R3
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -12(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label37

mov $str0, %eax	# Move str0,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label37:

mov -12(%ebp), %eax	# ArrayLength R3,R3
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -12(%ebp)

mov $2, %eax	# Move 2,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Mul R4,R3
mov -16(%ebp), %ebx
mul %ebx
mov %eax, -12(%ebp)

# Library __random(R3),R2
mov -12(%ebp), %eax
push %eax
call __random
add $4, %esp
mov %eax, -8(%ebp)

mov 8(%ebp), %eax	# Move this,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# MoveField R3.1,R3
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -12(%ebp)

mov -28(%ebp), %eax	# Move var1_i,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label38

mov $str0, %eax	# Move str0,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label38:

mov -12(%ebp), %eax	# ArrayLength R3,R6
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Compare R4,R6
cmp -16(%ebp), %eax

JG _end_label39

mov $str1, %eax	# Move str1,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label39:

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JGE _end_label40

mov $str2, %eax	# Move str2,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label40:

mov -12(%ebp), %eax	# MoveArray R2,R3[R4]
mov -16(%ebp), %ebx
mov -8(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov -28(%ebp), %eax	# Move var1_i,R5
mov %eax, -20(%ebp)

mov $1, %eax	# Move 1,R6
mov %eax, -24(%ebp)

mov -20(%ebp), %eax	# Add R6,R5
Add -24(%ebp), %eax
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Move R5,var1_i
mov %eax, -28(%ebp)

JMP _test_label34

_end_label34:

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_Quicksort_printArray:
push %ebp	# prologue
mov %esp, %ebp
sub $28, %esp

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_i
mov %eax, -28(%ebp)

mov $str3, %eax	# Move str3,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

_test_label41:

mov 8(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label43

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label43:

mov -4(%ebp), %eax	# ArrayLength R1,R1
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -4(%ebp)

mov -28(%ebp), %eax	# Move var1_i,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JL _true_label42

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label42

_true_label42:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label42:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label41

mov 8(%ebp), %eax	# Move this,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# MoveField R3.1,R3
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -12(%ebp)

mov -28(%ebp), %eax	# Move var1_i,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label44

mov $str0, %eax	# Move str0,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label44:

mov -12(%ebp), %eax	# ArrayLength R3,R6
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Compare R4,R6
cmp -16(%ebp), %eax

JG _end_label45

mov $str1, %eax	# Move str1,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label45:

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JGE _end_label46

mov $str2, %eax	# Move str2,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label46:

mov -12(%ebp), %eax	# MoveArray R3[R4],R5
mov -16(%ebp), %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -20(%ebp)

# Library __printi(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str4, %eax	# Move str4,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

mov -28(%ebp), %eax	# Move var1_i,R4
mov %eax, -16(%ebp)

mov $1, %eax	# Move 1,R5
mov %eax, -20(%ebp)

mov -16(%ebp), %eax	# Add R5,R4
Add -20(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move R4,var1_i
mov %eax, -28(%ebp)

JMP _test_label41

_end_label41:

mov $str5, %eax	# Move str5,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
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

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

mov 8(%ebp), %eax	# Move param1_args,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label48

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label48:

mov -8(%ebp), %eax	# ArrayLength R2,R2
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JNZ _true_label47

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label47

_true_label47:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label47:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label49

mov $str6, %eax	# Move str6,R3
mov %eax, -12(%ebp)

# Library __println(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

# Library __exit(R4),R3
mov -16(%ebp), %eax
push %eax
call __exit
add $4, %esp
mov %eax, -12(%ebp)

_end_label49:

mov 8(%ebp), %eax	# Move param1_args,R2
mov %eax, -8(%ebp)

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label50

mov $str0, %eax	# Move str0,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label50:

mov -8(%ebp), %eax	# ArrayLength R2,R5
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R3,R5
cmp -12(%ebp), %eax

JG _end_label51

mov $str1, %eax	# Move str1,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label51:

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JGE _end_label52

mov $str2, %eax	# Move str2,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label52:

mov -8(%ebp), %eax	# MoveArray R2[R3],R4
mov -12(%ebp), %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -16(%ebp)

mov $0, %eax	# Move 0,R5
mov %eax, -20(%ebp)

# Library __stoi(R4, R5),R1
mov -20(%ebp), %eax
push %eax
mov -16(%ebp), %eax
push %eax
call __stoi
add $8, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_n
mov %eax, -28(%ebp)

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

mov -28(%ebp), %eax	# Move var1_n,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JLE _true_label53

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label53

_true_label53:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label53:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label54

mov $str7, %eax	# Move str7,R3
mov %eax, -12(%ebp)

# Library __println(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

# Library __exit(R4),R3
mov -16(%ebp), %eax
push %eax
call __exit
add $4, %esp
mov %eax, -12(%ebp)

_end_label54:

# Library __allocateObject(8),R1
mov $8, %eax
push %eax
call __allocateObject
add $4, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField _DV_Quicksort,R1.0
mov $0, %ebx
mov $_DV_Quicksort, %ecx
mov %ecx, (%eax,%ebx,4)

mov -4(%ebp), %eax	# Move R1,var2_s
mov %eax, -24(%ebp)

mov -28(%ebp), %eax	# Move var1_n,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Mul 4,R1
mov $4, %ebx
mul %ebx
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JGE _end_label55

mov $str2, %eax	# Move str2,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label55:

# Library __allocateArray(R1),R1
mov -4(%ebp), %eax
push %eax
call __allocateArray
add $4, %esp
mov %eax, -4(%ebp)

mov -24(%ebp), %eax	# Move var2_s,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label56

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label56:

mov -8(%ebp), %eax	# MoveField R1,R2.1
mov $1, %ebx
mov -4(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov -24(%ebp), %eax	# Move var2_s,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label57

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label57:

mov -4(%ebp), %eax	# VirtualCall R1.2(),Rdummy
push %eax
mov (%eax), %eax
call *8(%eax)
add $4, %esp

mov -24(%ebp), %eax	# Move var2_s,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label58

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label58:

mov -4(%ebp), %eax	# VirtualCall R1.3(),Rdummy
push %eax
mov (%eax), %eax
call *12(%eax)
add $4, %esp

mov -24(%ebp), %eax	# Move var2_s,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label59

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label59:

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

mov -28(%ebp), %eax	# Move var1_n,R4
mov %eax, -16(%ebp)

mov $1, %eax	# Move 1,R5
mov %eax, -20(%ebp)

mov -16(%ebp), %eax	# Sub R5,R4
Sub -20(%ebp), %eax
mov %eax, -16(%ebp)

mov -4(%ebp), %eax	# VirtualCall R1.1(param1_low=R3, param2_high=R4),Rdummy
mov -16(%ebp), %ebx
push %ebx
mov -12(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *4(%eax)
add $12, %esp

mov -24(%ebp), %eax	# Move var2_s,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label60

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label60:

mov -4(%ebp), %eax	# VirtualCall R1.3(),Rdummy
push %eax
mov (%eax), %eax
call *12(%eax)
add $4, %esp

_PROGRAM_END:

# Library __exit(0),Rdummy
mov $0, %eax
push %eax
call __exit
add $4, %esp

