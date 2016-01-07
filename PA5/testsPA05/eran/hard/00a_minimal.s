.title	"00a_minimal.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_A:	.long _DV_A_func1,_DV_A_func2
_DV_B:	.long _DV_A_func1,_DV_B_func2,_DV_B_func3


# text (code) section
.text

# -------------------
.align 4
_DV_A_func1:
push %ebp	# prologue
mov %esp, %ebp
sub $4, %esp

mov $5, %eax	# Move 5,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_A_func2:
push %ebp	# prologue
mov %esp, %ebp
sub $4, %esp

mov $6, %eax	# Move 6,R1
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
sub $0, %esp

_PROGRAM_END:

# Library __exit(0),Rdummy
mov $0, %eax
push %eax
call __exit
add $4, %esp


# -------------------
.align 4
_DV_B_func2:
push %ebp	# prologue
mov %esp, %ebp
sub $4, %esp

mov $7, %eax	# Move 7,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret


# -------------------
.align 4
_DV_B_func3:
push %ebp	# prologue
mov %esp, %ebp
sub $4, %esp

mov $8, %eax	# Move 8,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret

