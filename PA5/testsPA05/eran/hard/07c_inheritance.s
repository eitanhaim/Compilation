.title	"07c_inheritance.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_MySuperClass:	.long _DV_MySuperClass_a,_DV_MySuperClass_b,_DV_MySuperClass_c,_DV_MySuperClass_d
_DV_Check:	.long 
_DV_MyInheritedClass:	.long _DV_MySuperClass_a,_DV_MySuperClass_b,_DV_MySuperClass_c,_DV_MySuperClass_d

.int 5
str0:	.string "hello"
.int 57
str1:	.string "Runtime error: Array allocation with negative array size!"
.int 40
str2:	.string "Runtime error: Null pointer dereference!"

# text (code) section
.text

# -------------------
.align 4
_DV_MySuperClass_a:
push %ebp	# prologue
mov %esp, %ebp
sub $0, %esp

mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_MySuperClass_b:
push %ebp	# prologue
mov %esp, %ebp
sub $4, %esp

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_MySuperClass_c:
push %ebp	# prologue
mov %esp, %ebp
sub $4, %esp

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_MySuperClass_d:
push %ebp	# prologue
mov %esp, %ebp
sub $4, %esp

mov $3, %eax	# Move 3,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Mul 4,R1
mov $4, %ebx
mul %ebx
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JGE _end_label1

mov $str1, %eax	# Move str1,R1
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

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $28, %esp

# Library __allocateObject(4),R1
mov $4, %eax
push %eax
call __allocateObject
add $4, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField _DV_MyInheritedClass,R1.0
mov $0, %ebx
mov $_DV_MyInheritedClass, %ecx
mov %ecx, (%eax,%ebx,4)

mov -4(%ebp), %eax	# Move R1,var1_obj
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Move var1_obj,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label2

mov $str2, %eax	# Move str2,R1
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

mov -24(%ebp), %eax	# Move var1_obj,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label3

mov $str2, %eax	# Move str2,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label3:

mov -4(%ebp), %eax	# VirtualCall R1.1(),R1
push %eax
mov (%eax), %eax
call *4(%eax)
add $4, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var2_x
mov %eax, -28(%ebp)

mov -24(%ebp), %eax	# Move var1_obj,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label4

mov $str2, %eax	# Move str2,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label4:

mov -28(%ebp), %eax	# Move var2_x,R3
mov %eax, -12(%ebp)

mov -4(%ebp), %eax	# VirtualCall R1.2(param1_a=R3),R1
mov -12(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *8(%eax)
add $8, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var3_str
mov %eax, -16(%ebp)

mov -24(%ebp), %eax	# Move var1_obj,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label5

mov $str2, %eax	# Move str2,R1
mov %eax, -4(%ebp)

# Library __print(R1),Rdummy
mov -4(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label5:

mov -16(%ebp), %eax	# Move var3_str,R3
mov %eax, -12(%ebp)

mov -4(%ebp), %eax	# VirtualCall R1.3(param1_s=R3),R1
mov -12(%ebp), %ebx
push %ebx
push %eax
mov (%eax), %eax
call *12(%eax)
add $8, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var4_arr
mov %eax, -20(%ebp)

mov -28(%ebp), %eax	# Move var2_x,R2
mov %eax, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __printi
add $4, %esp

mov -20(%ebp), %eax	# Move var4_arr,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare 0,R2
cmp $0, %eax

JNZ _end_label6

mov $str2, %eax	# Move str2,R2
mov %eax, -8(%ebp)

# Library __print(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __print
add $4, %esp

JMP _PROGRAM_END

_end_label6:

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

mov -16(%ebp), %eax	# Move var3_str,R2
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

