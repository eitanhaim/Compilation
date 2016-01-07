.title	"newClass.ic"

# global declarations
.global _ic_main

# data section
.data
.align 4

_DV_A:	.long _ic_main


# text (code) section
.text

# -------------------
.align 4
_ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $12, %esp

# Library __allocateObject(4),R1
mov &4, %eax
push %eax
call __allocateObject
add $4, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# MoveField _DV_A,R1.0
mov &0, %ebx
movl $_DV_A, (%eax,%ebx,4)

mov -4(%ebp), %eax	# Move R1,var1_b
mov %eax, -12(%ebp)

_PROGRAM_END:

# Library __exit(0),Rdummy
mov &0, %eax
push %eax
call __exit
add $4, %esp

