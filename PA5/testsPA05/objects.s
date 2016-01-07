.title	"objects.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_A:	.long _DV_A_m
_DV_B:	.long _DV_B_m
_DV_C:	.long 

.int 12
str0:	.string "A fields: "
.int 12
str1:	.string "B fields: "
.int 4
str2:	.string ", "
.int 2
str3:	.string ""
.int 42
str4:	.string "Runtime error: Null pointer dereference!"

# text (code) section
.text

# -------------------
.align 4
_DV_A_m:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

mov 4(%ebp), %eax	# Move this,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# MoveField R2.1,R2
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -8(%ebp)

# Library __itos(R2),R2
mov -8(%ebp), %eax
push %eax
call __itos
add $4, %esp
mov %eax, -8(%ebp)

# Library __stringCat(R1, R2),R1
mov -8(%ebp), %eax
push %eax
mov -4(%ebp), %eax
push %eax
call __stringCat
add $8, %esp
mov %eax, -4(%ebp)

# Library __println(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __println
add $4, %esp

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_A_m:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov $str1, %eax	# Move str1,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

mov 4(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField R1.1,R1
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

# Library __printi(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov $str2, %eax	# Move str2,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

mov 4(%ebp), %eax	# Move this,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField R1.2,R1
mov $2, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -4(%ebp)

# Library __printb(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __printb
add $4, %esp

mov $str3, %eax	# Move str3,R1
mov %eax, -4(%ebp)

# Library __println(R1),Rdummy
mov -4(%ebp), %eax
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

mov -4(%ebp), %eax	# Move R1,var1_oa
mov %eax, -24(%ebp)

# Library __allocateObject(12),R2
mov $12, %eax
push %eax
call __allocateObject
add $4, %esp
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# MoveField _DV_B,R2.0
mov $0, %ebx
mov $_DV_B, %ecx
mov %ecx, (%eax,%ebx,4)

mov -8(%ebp), %eax	# Move R2,var2_ob
mov %eax, -28(%ebp)

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

mov 8(%ebp), %eax	# Move param1_args,R5
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Compare 0,R5
cmp $0, %eax

JNZ _end_label2

mov $str4, %eax	# Move str4,R4
mov %eax, -16(%ebp)

# Library __print(R4),Rdummy
mov -16(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label2:

mov -20(%ebp), %eax	# ArrayLength R5,R4
mov -4(%eax), %eax)
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Compare R3,R4
cmp -12(%ebp), %eax

JNZ _true_label1

mov $0, %eax	# Move 0,R3
mov %eax, -12(%ebp)

JMP _end_label1

_true_label1:

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

_end_label1:

mov -12(%ebp), %eax	# Compare 1,R3
cmp $1, %eax

JNZ _end_label3

mov -28(%ebp), %eax	# Move var2_ob,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move R4,var1_oa
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Move var1_oa,R3
mov %eax, -12(%ebp)

_end_label3:

mov $412, %eax	# Move 412,R4
mov %eax, -16(%ebp)

mov -24(%ebp), %eax	# Move var1_oa,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label4

mov $str4, %eax	# Move str4,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label4:

mov -12(%ebp), %eax	# MoveField R4,R3.1
mov $1, %ebx
mov -16(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov -12(%ebp), %eax	# MoveField R3.1,R3
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -12(%ebp)

mov $413, %eax	# Move 413,R4
mov %eax, -16(%ebp)

mov -28(%ebp), %eax	# Move var2_ob,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label5

mov $str4, %eax	# Move str4,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label5:

mov -12(%ebp), %eax	# MoveField R4,R3.1
mov $1, %ebx
mov -16(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov -12(%ebp), %eax	# MoveField R3.1,R3
mov $1, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -12(%ebp)

mov $1, %eax	# Move 1,R4
mov %eax, -16(%ebp)

mov -28(%ebp), %eax	# Move var2_ob,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label6

mov $str4, %eax	# Move str4,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label6:

mov -12(%ebp), %eax	# MoveField R4,R3.2
mov $2, %ebx
mov -16(%ebp), %ecx
mov %ecx, (%eax,%ebx,4)

mov -12(%ebp), %eax	# MoveField R3.2,R3
mov $2, %ebx
mov (%eax,%ebx,4), %ecx
mov %ecx, -12(%ebp)

mov -24(%ebp), %eax	# Move var1_oa,R3
mov %eax, -12(%ebp)

mov -12(%ebp), %eax	# Compare 0,R3
cmp $0, %eax

JNZ _end_label7

mov $str4, %eax	# Move str4,R3
mov %eax, -12(%ebp)

# Library __print(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label7:

mov -12(%ebp), %eax	# VirtualCall R3.0(),Rdummy
push %eax
mov (%eax), %eax
call *-4(%eax)
add $4, %esp

_PROGRAM_END:

# Library __exit(0),Rdummy
mov $0, %eax
push %eax
call __exit
add $4, %esp

