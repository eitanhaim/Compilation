.title	"22c_arrays.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_MainClass:	.long 
_DV_Arrays:	.long 

.int 57
str0:	.string "Runtime error: Array allocation with negative array size!"
.int 40
str1:	.string "Runtime error: Null pointer dereference!"
.int 41
str2:	.string "Runtime error: Array index out of bounds!"
.int 0
str3:	.string ""
.int 34
str4:	.string "Should not print this or get here."
.int 2
str5:	.string "[ "
.int 2
str6:	.string ", "
.int 2
str7:	.string " ]"

# text (code) section
.text

# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $24, %esp

mov $5, %eax	# Move 5,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Mul 4,R1
mov $4, %ebx
mul %ebx
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JGE _end_label1

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label1:

# Library __allocateArray(R1),R1
mov -4(%ebp), %eax
push %eax
call __allocateArray
add $4, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_arr
mov %eax, -24(%ebp)

mov $10, %eax	# Move 10,R1
mov %eax, -4(%ebp)

mov -24(%ebp), %eax	# Move var1_arr,R2
mov %eax, -8(%ebp)

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label2

mov $str1, %eax	# Move str1,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label2:

mov -8(%ebp), %eax	# ArrayLength R2,R5
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R3,R5
cmp -12(%ebp), %eax

JG _end_label3

mov $str2, %eax	# Move str2,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label3:

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JGE _end_label4

mov $str0, %eax	# Move str0,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label4:

mov -8(%ebp), %eax	# MoveArray R1,R2[R3]
mov -12(%ebp), %ebx
mov -4(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov $20, %eax	# Move 20,R1
mov %eax, -4(%ebp)

mov -24(%ebp), %eax	# Move var1_arr,R2
mov %eax, -8(%ebp)

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label5

mov $str1, %eax	# Move str1,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label5:

mov -8(%ebp), %eax	# ArrayLength R2,R5
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R3,R5
cmp -12(%ebp), %eax

JG _end_label6

mov $str2, %eax	# Move str2,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label6:

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JGE _end_label7

mov $str0, %eax	# Move str0,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label7:

mov -8(%ebp), %eax	# MoveArray R1,R2[R3]
mov -12(%ebp), %ebx
mov -4(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov $40, %eax	# Move 40,R1
mov %eax, -4(%ebp)

mov -24(%ebp), %eax	# Move var1_arr,R2
mov %eax, -8(%ebp)

mov $2, %eax	# Move 2,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label8

mov $str1, %eax	# Move str1,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label8:

mov -8(%ebp), %eax	# ArrayLength R2,R5
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R3,R5
cmp -12(%ebp), %eax

JG _end_label9

mov $str2, %eax	# Move str2,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label9:

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JGE _end_label10

mov $str0, %eax	# Move str0,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label10:

mov -8(%ebp), %eax	# MoveArray R1,R2[R3]
mov -12(%ebp), %ebx
mov -4(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov $80, %eax	# Move 80,R1
mov %eax, -4(%ebp)

mov -24(%ebp), %eax	# Move var1_arr,R2
mov %eax, -8(%ebp)

mov $3, %eax	# Move 3,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label11

mov $str1, %eax	# Move str1,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label11:

mov -8(%ebp), %eax	# ArrayLength R2,R5
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R3,R5
cmp -12(%ebp), %eax

JG _end_label12

mov $str2, %eax	# Move str2,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label12:

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JGE _end_label13

mov $str0, %eax	# Move str0,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label13:

mov -8(%ebp), %eax	# MoveArray R1,R2[R3]
mov -12(%ebp), %ebx
mov -4(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov $160, %eax	# Move 160,R1
mov %eax, -4(%ebp)

mov -24(%ebp), %eax	# Move var1_arr,R2
mov %eax, -8(%ebp)

mov $4, %eax	# Move 4,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label14

mov $str1, %eax	# Move str1,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label14:

mov -8(%ebp), %eax	# ArrayLength R2,R5
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R3,R5
cmp -12(%ebp), %eax

JG _end_label15

mov $str2, %eax	# Move str2,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label15:

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JGE _end_label16

mov $str0, %eax	# Move str0,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label16:

mov -8(%ebp), %eax	# MoveArray R1,R2[R3]
mov -12(%ebp), %ebx
mov -4(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov -24(%ebp), %eax	# Move var1_arr,R2
mov %eax, -8(%ebp)

# StaticCall _DV_Arrays_printArr(param1_a=R2),Rdummy
mov -8(%ebp), %eax
push %eax
call _DV_Arrays_printArr
add $4, %esp

mov -24(%ebp), %eax	# Move var1_arr,R2
mov %eax, -8(%ebp)

mov $2, %eax	# Move 2,R3
mov %eax, -12(%ebp)

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Sub R3,R4
Sub -12(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move R4,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
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

mov -8(%ebp), %eax	# ArrayLength R2,R5
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R3,R5
cmp -12(%ebp), %eax

JG _end_label18

mov $str2, %eax	# Move str2,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label18:

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JGE _end_label19

mov $str0, %eax	# Move str0,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label19:

mov -8(%ebp), %eax	# MoveArray R2[R3],R4
mov -12(%ebp), %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -16(%ebp)

# Library __printi(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str3, %eax	# Move str3,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $str4, %eax	# Move str4,R2
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
_DV_Arrays_buildArr:
push %ebp	# prologue
mov %esp, %ebp
sub $32, %esp

mov 8(%ebp), %eax	# Move param1_size,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Mul 4,R1
mov $4, %ebx
mul %ebx
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JGE _end_label20

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label20:

# Library __allocateArray(R1),R1
mov -4(%ebp), %eax
push %eax
call __allocateArray
add $4, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_a
mov %eax, -28(%ebp)

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var2_i
mov %eax, -32(%ebp)

_test_label21:

mov -28(%ebp), %eax	# Move var1_a,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label23

mov $str1, %eax	# Move str1,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label23:

mov -4(%ebp), %eax	# ArrayLength R1,R1
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -4(%ebp)

mov -32(%ebp), %eax	# Move var2_i,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JL _true_label22

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label22

_true_label22:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label22:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label21

mov 12(%ebp), %eax	# Move param2_val,R2
mov %eax, -8(%ebp)

mov -28(%ebp), %eax	# Move var1_a,R3
mov %eax, -12(%ebp)

mov -32(%ebp), %eax	# Move var2_i,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label24

mov $str1, %eax	# Move str1,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label24:

mov -12(%ebp), %eax	# ArrayLength R3,R6
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Compare R4,R6
cmp -16(%ebp), %eax

JG _end_label25

mov $str2, %eax	# Move str2,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label25:

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JGE _end_label26

mov $str0, %eax	# Move str0,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label26:

mov -12(%ebp), %eax	# MoveArray R2,R3[R4]
mov -16(%ebp), %ebx
mov -8(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov -32(%ebp), %eax	# Move var2_i,R5
mov %eax, -20(%ebp)

mov $1, %eax	# Move 1,R6
mov %eax, -24(%ebp)

mov -20(%ebp), %eax	# Add R6,R5
Add -24(%ebp), %eax
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Move R5,var2_i
mov %eax, -32(%ebp)

JMP _test_label21

_end_label21:

mov -28(%ebp), %eax	# Move var1_a,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_Arrays_sumArr:
push %ebp	# prologue
mov %esp, %ebp
sub $44, %esp

mov 8(%ebp), %eax	# Move param1_a,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label27

mov $str1, %eax	# Move str1,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label27:

mov -4(%ebp), %eax	# ArrayLength R1,R1
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_size
mov %eax, -36(%ebp)

mov -36(%ebp), %eax	# Move var1_size,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Mul 4,R1
mov $4, %ebx
mul %ebx
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JGE _end_label28

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label28:

# Library __allocateArray(R1),R1
mov -4(%ebp), %eax
push %eax
call __allocateArray
add $4, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var2_sum
mov %eax, -44(%ebp)

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var3_i
mov %eax, -40(%ebp)

_test_label29:

mov 8(%ebp), %eax	# Move param1_a,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label31

mov $str1, %eax	# Move str1,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label31:

mov -4(%ebp), %eax	# ArrayLength R1,R1
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -4(%ebp)

mov -40(%ebp), %eax	# Move var3_i,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JL _true_label30

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label30

_true_label30:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label30:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label29

mov 8(%ebp), %eax	# Move param1_a,R2
mov %eax, -8(%ebp)

mov -40(%ebp), %eax	# Move var3_i,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label32

mov $str1, %eax	# Move str1,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label32:

mov -8(%ebp), %eax	# ArrayLength R2,R5
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R3,R5
cmp -12(%ebp), %eax

JG _end_label33

mov $str2, %eax	# Move str2,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label33:

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JGE _end_label34

mov $str0, %eax	# Move str0,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label34:

mov -8(%ebp), %eax	# MoveArray R2[R3],R4
mov -12(%ebp), %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -16(%ebp)

mov 12(%ebp), %eax	# Move param2_b,R5
mov %eax, -20(%ebp)

mov -40(%ebp), %eax	# Move var3_i,R6
mov %eax, -24(%ebp)

mov -20(%ebp), %eax	# Compare 0,R5
cmp $0, %eax

JNZ _end_label35

mov $str1, %eax	# Move str1,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label35:

mov -20(%ebp), %eax	# ArrayLength R5,R8
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -32(%ebp)

mov -32(%ebp), %eax	# Compare R6,R8
cmp -24(%ebp), %eax

JG _end_label36

mov $str2, %eax	# Move str2,R8
mov %eax, -32(%ebp)

# Library __print(R8),Rdummy
mov -32(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label36:

mov -24(%ebp), %eax	# Compare 0,R6
cmp $0, %eax

JGE _end_label37

mov $str0, %eax	# Move str0,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label37:

mov -20(%ebp), %eax	# MoveArray R5[R6],R7
mov -24(%ebp), %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -28(%ebp)

mov -16(%ebp), %eax	# Move R4,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Add R7,R2
Add -28(%ebp), %eax
mov %eax, -8(%ebp)

mov -44(%ebp), %eax	# Move var2_sum,R3
mov %eax, -12(%ebp)

mov -40(%ebp), %eax	# Move var3_i,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label38

mov $str1, %eax	# Move str1,R4
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

mov $str2, %eax	# Move str2,R6
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

mov $str0, %eax	# Move str0,R4
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

mov -40(%ebp), %eax	# Move var3_i,R5
mov %eax, -20(%ebp)

mov $1, %eax	# Move 1,R6
mov %eax, -24(%ebp)

mov -20(%ebp), %eax	# Add R6,R5
Add -24(%ebp), %eax
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Move R5,var3_i
mov %eax, -40(%ebp)

JMP _test_label29

_end_label29:

mov -44(%ebp), %eax	# Move var2_sum,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_Arrays_printArr:
push %ebp	# prologue
mov %esp, %ebp
sub $28, %esp

mov $str5, %eax	# Move str5,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_i
mov %eax, -28(%ebp)

_test_label41:

mov 8(%ebp), %eax	# Move param1_a,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label43

mov $str1, %eax	# Move str1,R1
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

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

mov -4(%ebp), %eax	# Sub R2,R1
Sub -8(%ebp), %eax
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

mov 8(%ebp), %eax	# Move param1_a,R3
mov %eax, -12(%ebp)

mov -28(%ebp), %eax	# Move var1_i,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label44

mov $str1, %eax	# Move str1,R4
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

mov $str2, %eax	# Move str2,R6
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

mov $str0, %eax	# Move str0,R4
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

mov $str6, %eax	# Move str6,R4
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

mov 8(%ebp), %eax	# Move param1_a,R2
mov %eax, -8(%ebp)

mov -28(%ebp), %eax	# Move var1_i,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label47

mov $str1, %eax	# Move str1,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label47:

mov -8(%ebp), %eax	# ArrayLength R2,R5
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R3,R5
cmp -12(%ebp), %eax

JG _end_label48

mov $str2, %eax	# Move str2,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label48:

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JGE _end_label49

mov $str0, %eax	# Move str0,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label49:

mov -8(%ebp), %eax	# MoveArray R2[R3],R4
mov -12(%ebp), %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -16(%ebp)

# Library __printi(R4),Rdummy
mov -16(%ebp), %eax
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

mov $str3, %eax	# Move str3,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov %ebp, %esp	# epilogue
pop %ebp
ret

