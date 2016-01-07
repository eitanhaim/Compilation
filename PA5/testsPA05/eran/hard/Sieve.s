.title	"Sieve.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_Sieve:	.long _DV_Sieve_initArray,_DV_Sieve_sieveAll,_DV_Sieve_sieve,_DV_Sieve_printPrimes,_DV_Sieve_test

.int 40
str0:	.string "Runtime error: Null pointer dereference!"
.int 41
str1:	.string "Runtime error: Array index out of bounds!"
.int 57
str2:	.string "Runtime error: Array allocation with negative array size!"
.int 17
str3:	.string "Primes less than "
.int 2
str4:	.string ": "
.int 1
str5:	.string " "
.int 19
str6:	.string "Unspecified number."
.int 0
str7:	.string ""
.int 20
str8:	.string "Invalid array length"

# text (code) section
.text

# -------------------
.align 4
_DV_Sieve_initArray:
push %ebp	# prologue
mov %esp, %ebp
sub $28, %esp

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_i
mov %eax, -28(%ebp)

_test_label1:

mov 8(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label3

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label3:

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

mov -28(%ebp), %eax	# Move var1_i,R2
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

JNZ _end_label4

mov $str0, %eax	# Move str0,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label4:

mov -12(%ebp), %eax	# ArrayLength R3,R6
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Compare R4,R6
cmp -16(%ebp), %eax

JG _end_label5

mov $str1, %eax	# Move str1,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label5:

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JGE _end_label6

mov $str2, %eax	# Move str2,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label6:

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

JMP _test_label1

_end_label1:

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_Sieve_sieveAll:
push %ebp	# prologue
mov %esp, %ebp
sub $20, %esp

mov $2, %eax	# Move 2,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_i
mov %eax, -20(%ebp)

_test_label7:

mov 8(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

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

mov -4(%ebp), %eax	# ArrayLength R1,R1
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -4(%ebp)

mov -20(%ebp), %eax	# Move var1_i,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JL _true_label8

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label8

_true_label8:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label8:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label7

mov 8(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label10

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label10:

mov -20(%ebp), %eax	# Move var1_i,R4
mov %eax, -16(%ebp)

mov -8(%ebp), %eax	# VirtualCall R2.2(param1_n=R4),Rdummy
mov -16(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *8(%eax)
add $8, %esp

mov -20(%ebp), %eax	# Move var1_i,R3
mov %eax, -12(%ebp)

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Add R4,R3
Add -16(%ebp), %eax
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Move R3,var1_i
mov %eax, -20(%ebp)

JMP _test_label7

_end_label7:

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_Sieve_sieve:
push %ebp	# prologue
mov %esp, %ebp
sub $28, %esp

mov $2, %eax	# Move 2,R1
mov %eax, -4(%ebp)

mov 12(%ebp), %eax	# Move param1_n,R2
mov %eax, -8(%ebp)

mov -4(%ebp), %eax	# Mul R2,R1
mov -8(%ebp), %ebx
mul %ebx
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_i
mov %eax, -28(%ebp)

_test_label11:

mov 8(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

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

JL _true_label12

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label12

_true_label12:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label12:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label11

mov $0, %eax	# Move 0,R2
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

mov -12(%ebp), %eax	# ArrayLength R3,R6
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Compare R4,R6
cmp -16(%ebp), %eax

JG _end_label15

mov $str1, %eax	# Move str1,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label15:

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JGE _end_label16

mov $str2, %eax	# Move str2,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label16:

mov -12(%ebp), %eax	# MoveArray R2,R3[R4]
mov -16(%ebp), %ebx
mov -8(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov -28(%ebp), %eax	# Move var1_i,R5
mov %eax, -20(%ebp)

mov 12(%ebp), %eax	# Move param1_n,R6
mov %eax, -24(%ebp)

mov -20(%ebp), %eax	# Add R6,R5
Add -24(%ebp), %eax
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Move R5,var1_i
mov %eax, -28(%ebp)

JMP _test_label11

_end_label11:

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_Sieve_printPrimes:
push %ebp	# prologue
mov %esp, %ebp
sub $32, %esp

mov $2, %eax	# Move 2,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_i
mov %eax, -32(%ebp)

mov $str3, %eax	# Move str3,R2
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

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label17

mov $str0, %eax	# Move str0,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label17:

mov -8(%ebp), %eax	# ArrayLength R2,R2
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str4, %eax	# Move str4,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

_test_label18:

mov 8(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label20

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label20:

mov -4(%ebp), %eax	# ArrayLength R1,R1
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -4(%ebp)

mov -32(%ebp), %eax	# Move var1_i,R2
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

JNZ _end_label18

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

mov 8(%ebp), %eax	# Move this,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# MoveField R3.1,R3
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -12(%ebp)

mov -32(%ebp), %eax	# Move var1_i,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label22

mov $str0, %eax	# Move str0,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label22:

mov -12(%ebp), %eax	# ArrayLength R3,R6
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Compare R4,R6
cmp -16(%ebp), %eax

JG _end_label23

mov $str1, %eax	# Move str1,R6
mov %eax, -24(%ebp)

# Library __print(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label23:

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JGE _end_label24

mov $str2, %eax	# Move str2,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label24:

mov -12(%ebp), %eax	# MoveArray R3[R4],R5
mov -16(%ebp), %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -20(%ebp)

mov -20(%ebp), %eax	# Compare R2,R5
cmp -8(%ebp), %eax

JNZ _true_label21

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label21

_true_label21:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label21:

mov -8(%ebp), %eax	# Compare 1,R2
cmp $1, %eax

JNZ _end_label25

mov 8(%ebp), %eax	# Move this,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# MoveField R4.1,R4
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -16(%ebp)

mov -32(%ebp), %eax	# Move var1_i,R5
mov %eax, -20(%ebp)

mov -16(%ebp), %eax	# Compare 0,R4
cmp $0, %eax

JNZ _end_label26

mov $str0, %eax	# Move str0,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label26:

mov -16(%ebp), %eax	# ArrayLength R4,R7
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -28(%ebp)

mov -28(%ebp), %eax	# Compare R5,R7
cmp -20(%ebp), %eax

JG _end_label27

mov $str1, %eax	# Move str1,R7
mov %eax, -28(%ebp)

# Library __print(R7),Rdummy
mov -28(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label27:

mov -20(%ebp), %eax	# Compare 0,R5
cmp $0, %eax

JGE _end_label28

mov $str2, %eax	# Move str2,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label28:

mov -16(%ebp), %eax	# MoveArray R4[R5],R6
mov -20(%ebp), %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -24(%ebp)

# Library __printi(R6),Rdummy
mov -24(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str5, %eax	# Move str5,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

_end_label25:

mov -32(%ebp), %eax	# Move var1_i,R5
mov %eax, -20(%ebp)

mov $1, %eax	# Move 1,R6
mov %eax, -24(%ebp)

mov -20(%ebp), %eax	# Add R6,R5
Add -24(%ebp), %eax
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Move R5,var1_i
mov %eax, -32(%ebp)

JMP _test_label18

_end_label18:

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $12, %esp

# Library __allocateObject(8),R1
mov $8, %eax
push %eax
call __allocateObject
add $4, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField _DV_Sieve,R1.0
mov $0, %ebx
mov $_DV_Sieve, %ecx
mov %ecx, (%eax,%ebx,4)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label29

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label29:

mov 8(%ebp), %eax	# Move param1_args,R3
mov %eax, -12(%ebp)

mov -4(%ebp), %eax	# VirtualCall R1.5(param1_args=R3),Rdummy
mov -12(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *20(%eax)
add $8, %esp

_PROGRAM_END:

# Library __exit(0),Rdummy
mov $0, %eax
push %eax
call __exit
add $4, %esp


# -------------------
.align 4
_DV_Sieve_test:
push %ebp	# prologue
mov %esp, %ebp
sub $24, %esp

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

mov 12(%ebp), %eax	# Move param1_args,R2
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

mov -8(%ebp), %eax	# ArrayLength R2,R2
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JNZ _true_label30

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label30

_true_label30:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label30:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label32

mov $str6, %eax	# Move str6,R3
mov %eax, -12(%ebp)

# Library __println(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __println
add $4, %esp

mov %ebp, %esp	# epilogue
pop %ebp
ret

_end_label32:

mov $str7, %eax	# Move str7,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov 12(%ebp), %eax	# Move param1_args,R2
mov %eax, -8(%ebp)

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label33

mov $str0, %eax	# Move str0,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label33:

mov -8(%ebp), %eax	# ArrayLength R2,R5
mov -4(%eax), %eax
mov $4, %ebx
mov $0, %edx
div %ebx
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare R3,R5
cmp -12(%ebp), %eax

JG _end_label34

mov $str1, %eax	# Move str1,R5
mov %eax, -20(%ebp)

# Library __print(R5),Rdummy
mov -20(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label34:

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JGE _end_label35

mov $str2, %eax	# Move str2,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label35:

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
mov %eax, -24(%ebp)

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

mov -24(%ebp), %eax	# Move var1_n,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JLE _true_label36

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label36

_true_label36:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label36:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label37

mov $str8, %eax	# Move str8,R3
mov %eax, -12(%ebp)

# Library __println(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __println
add $4, %esp

mov %ebp, %esp	# epilogue
pop %ebp
ret

_end_label37:

mov -24(%ebp), %eax	# Move var1_n,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Mul 4,R1
mov $4, %ebx
mul %ebx
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JGE _end_label38

mov $str2, %eax	# Move str2,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label38:

# Library __allocateArray(R1),R1
mov -4(%ebp), %eax
push %eax
call __allocateArray
add $4, %esp
mov %eax, -4(%ebp)

mov 8(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# MoveField R1,R2.1
mov $1, %ebx
mov -4(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov 8(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label39

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label39:

mov -4(%ebp), %eax	# VirtualCall R1.0(),Rdummy
push %eax
mov (%eax), %eax
call *0(%eax)
add $4, %esp

mov 8(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label40

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label40:

mov -4(%ebp), %eax	# VirtualCall R1.1(),Rdummy
push %eax
mov (%eax), %eax
call *4(%eax)
add $4, %esp

mov 8(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label41

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label41:

mov -4(%ebp), %eax	# VirtualCall R1.3(),Rdummy
push %eax
mov (%eax), %eax
call *12(%eax)
add $4, %esp

mov $str7, %eax	# Move str7,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov %ebp, %esp	# epilogue
pop %ebp
ret

