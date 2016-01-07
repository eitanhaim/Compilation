.title	"05a_objects.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_A:	.long _DV_A_init,_DV_A_inc,_DV_A_print
_DV_Check:	.long 

.int 40
str0:	.string "Runtime error: Null pointer dereference!"
.int 0
str1:	.string ""

# text (code) section
.text

# -------------------
.align 4
_DV_A_init:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov $0, %eax	# Move 0,R1
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
_DV_A_inc:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov 8(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label1

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label1:

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

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_A_print:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

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

mov $str1, %eax	# Move str1,R2
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
sub $20, %esp

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

mov -4(%ebp), %eax	# Move R1,var1_a1
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move var1_a1,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label2

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label2:

mov -4(%ebp), %eax	# VirtualCall R1.0(),Rdummy
push %eax
mov (%eax), %eax
call *0(%eax)
add $4, %esp

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

mov -4(%ebp), %eax	# Move R1,var2_a2
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Move var2_a2,R1
mov %eax, -4(%ebp)

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

mov -4(%ebp), %eax	# VirtualCall R1.0(),Rdummy
push %eax
mov (%eax), %eax
call *0(%eax)
add $4, %esp

mov -20(%ebp), %eax	# Move var2_a2,R2
mov %eax, -8(%ebp)

mov -16(%ebp), %eax	# Move var1_a1,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JZ _true_label4

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label4

_true_label4:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label4:

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

mov -16(%ebp), %eax	# Move var1_a1,R2
mov %eax, -8(%ebp)

mov -16(%ebp), %eax	# Move var1_a1,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JZ _true_label5

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label5

_true_label5:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label5:

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

mov -16(%ebp), %eax	# Move var1_a1,R1
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

mov -4(%ebp), %eax	# VirtualCall R1.1(),Rdummy
push %eax
mov (%eax), %eax
call *4(%eax)
add $4, %esp

mov -20(%ebp), %eax	# Move var2_a2,R2
mov %eax, -8(%ebp)

mov -16(%ebp), %eax	# Move var1_a1,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JZ _true_label7

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label7

_true_label7:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label7:

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

mov -20(%ebp), %eax	# Move var2_a2,R1
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

mov -4(%ebp), %eax	# VirtualCall R1.1(),Rdummy
push %eax
mov (%eax), %eax
call *4(%eax)
add $4, %esp

mov -20(%ebp), %eax	# Move var2_a2,R2
mov %eax, -8(%ebp)

mov -16(%ebp), %eax	# Move var1_a1,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare R2,R3
cmp -8(%ebp), %eax

JZ _true_label9

mov $0, %eax	# Move 0,R2
mov %eax, -8(%ebp)

JMP _end_label9

_true_label9:

mov $1, %eax	# Move 1,R2
mov %eax, -8(%ebp)

_end_label9:

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

