.title	"fib.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_Fibonacci:	.long 

.int 40
str0:	.string "Runtime error: Null pointer dereference!"
.int 41
str1:	.string "Runtime error: Array index out of bounds!"
.int 57
str2:	.string "Runtime error: Array allocation with negative array size!"
.int 0
str3:	.string ""

# text (code) section
.text

# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $28, %esp

mov 8(%ebp), %eax	# Move param1_args,R1
mov %eax, -4(%ebp)

mov $0, %eax	# Move 0,R2
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

mov -12(%ebp), %eax	# Move R3,var1_s
mov %eax, -24(%ebp)

mov -24(%ebp), %eax	# Move var1_s,R2
mov %eax, -8(%ebp)

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

mov $0, %eax	# Move 0,R4
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Sub R3,R4
Sub -12(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move R4,R3
mov %eax, -12(%ebp)

# Library __stoi(R2, R3),R1
mov -12(%ebp), %eax
push %eax
mov -8(%ebp), %eax
push %eax
call __stoi
add $8, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var2_n
mov %eax, -28(%ebp)

mov -28(%ebp), %eax	# Move var2_n,R2
mov %eax, -8(%ebp)

# StaticCall _DV_Fibonacci_fib(param1_n=R2),R1
mov -8(%ebp), %eax
push %eax
call _DV_Fibonacci_fib
add $4, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var3_r
mov %eax, -20(%ebp)

mov -20(%ebp), %eax	# Move var3_r,R2
mov %eax, -8(%ebp)

# Library __printi(R2),Rdummy
mov -8(%ebp), %eax
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

_PROGRAM_END:

# Library __exit(0),Rdummy
mov $0, %eax
push %eax
call __exit
add $4, %esp


# -------------------
.align 4
_DV_Fibonacci_fib:
push %ebp	# prologue
mov %esp, %ebp
sub $16, %esp

mov $2, %eax	# Move 2,R1
mov %eax, -4(%ebp)

mov 8(%ebp), %eax	# Move param1_n,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JL _true_label4

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label4

_true_label4:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label4:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label5

mov 8(%ebp), %eax	# Move param1_n,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Return R2
mov %ebp, %esp	# epilogue
pop %ebp
ret

_end_label5:

mov 8(%ebp), %eax	# Move param1_n,R2
mov %eax, -8(%ebp)

mov $1, %eax	# Move 1,R3
mov %eax, -12(%ebp)

mov -8(%ebp), %eax	# Sub R3,R2
Sub -12(%ebp), %eax
mov %eax, -8(%ebp)

# StaticCall _DV_Fibonacci_fib(param1_n=R2),R1
mov -8(%ebp), %eax
push %eax
call _DV_Fibonacci_fib
add $4, %esp
mov %eax, -4(%ebp)

mov 8(%ebp), %eax	# Move param1_n,R3
mov %eax, -12(%ebp)

mov $2, %eax	# Move 2,R4
mov %eax, -16(%ebp)

mov -12(%ebp), %eax	# Sub R4,R3
Sub -16(%ebp), %eax
mov %eax, -12(%ebp)

# StaticCall _DV_Fibonacci_fib(param1_n=R3),R2
mov -12(%ebp), %eax
push %eax
call _DV_Fibonacci_fib
add $4, %esp
mov %eax, -8(%ebp)

mov -4(%ebp), %eax	# Add R2,R1
Add -8(%ebp), %eax
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret

