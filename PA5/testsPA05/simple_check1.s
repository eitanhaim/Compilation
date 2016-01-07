.title	"simple_check1.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_A:	.long _DV_A_foo
_DV_C:	.long 
_DV_D:	.long 

.int 42
str0:	.string "Runtime error: Null pointer dereference!"

# text (code) section
.text

# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $16, %esp

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
mov %eax, -16(%ebp)

# Library __allocateObject(8),R3
mov $8, %eax
push %eax
call __allocateObject
add $4, %esp
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# MoveField _DV_D,R3.0
mov $0, %ebx
mov $_DV_D, %ecx
mov %ecx, (%eax,%ebx,4)

mov -16(%ebp), %eax	# Move var1_a,R2
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

mov -8(%ebp), %eax	# MoveField R3,R2.1
mov $1, %ebx
mov -12(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov -8(%ebp), %eax	# MoveField R2.1,R2
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -8(%ebp)

mov -16(%ebp), %eax	# Move var1_a,R2
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

mov -8(%ebp), %eax	# VirtualCall R2.1(),R2
push %eax
mov (%eax), %eax
call *0(%eax)
add $4, %esp
mov %eax, -8(%ebp)

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


# -------------------
.align 4
_DV_A_foo:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

# Library __allocateObject(8),R2
mov $8, %eax
push %eax
call __allocateObject
add $4, %esp
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# MoveField _DV_C,R2.0
mov $0, %ebx
mov $_DV_C, %ecx
mov %ecx, (%eax,%ebx,4)

mov 4(%ebp), %eax	# Move this,R1
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

mov -4(%ebp), %eax	# MoveField R2,R1.1
mov $1, %ebx
mov -8(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

mov $5, %eax	# Move 5,R2
mov %eax, -8(%ebp)

mov 4(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label4

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label4:

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label5

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label5:

mov -4(%ebp), %eax	# MoveField R2,R1.1
mov $1, %ebx
mov -8(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

mov 4(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

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

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

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

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret

