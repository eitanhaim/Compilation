.title	"01a_strings.ic"

# global declarations
.global __ic_main

# data section
.data
.align 4

_DV_IFS:	.long 

.int 1
str0:	.string "a"
.int 1
str1:	.string "b"
.int 1
str2:	.string "c"
.int 2
str3:	.string "ab"
.int 7
str4:	.string "equals1"
.int 7
str5:	.string "equals2"
.int 7
str6:	.string "equals3"
.int 7
str7:	.string "equals4"
.int 3
str8:	.string "acb"
.int 34
str9:	.string "not equals (should not be printed)"
.int 40
str10:	.string "Runtime error: Null pointer dereference!"

# text (code) section
.text

# -------------------
.align 4
__ic_main:
push %ebp	# prologue
mov %esp, %ebp
sub $44, %esp

mov $str0, %eax	# Move str0,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var1_a
mov %eax, -24(%ebp)

mov $str1, %eax	# Move str1,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var2_b
mov %eax, -32(%ebp)

mov $str2, %eax	# Move str2,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var3_c
mov %eax, -36(%ebp)

mov -24(%ebp), %eax	# Move var1_a,R2
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov -24(%ebp), %eax	# Move var1_a,R2
mov %eax, -8(%ebp)

mov -32(%ebp), %eax	# Move var2_b,R3
mov %eax, -12(%ebp)

# Library __stringCat(R2, R3),R2
mov -12(%ebp), %eax
push %eax
mov -8(%ebp), %eax
push %eax
call __stringCat
add $8, %esp
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov -36(%ebp), %eax	# Move var3_c,R2
mov %eax, -8(%ebp)

mov -32(%ebp), %eax	# Move var2_b,R3
mov %eax, -12(%ebp)

# Library __stringCat(R2, R3),R2
mov -12(%ebp), %eax
push %eax
mov -8(%ebp), %eax
push %eax
call __stringCat
add $8, %esp
mov %eax, -8(%ebp)

mov -24(%ebp), %eax	# Move var1_a,R3
mov %eax, -12(%ebp)

# Library __stringCat(R2, R3),R2
mov -12(%ebp), %eax
push %eax
mov -8(%ebp), %eax
push %eax
call __stringCat
add $8, %esp
mov %eax, -8(%ebp)

# Library __println(R2),Rdummy
mov -8(%ebp), %eax
push %eax
call __println
add $4, %esp

mov $str3, %eax	# Move str3,R2
mov %eax, -8(%ebp)

# StaticCall _DV_IFS_len(param1_s=R2),R1
mov -8(%ebp), %eax
push %eax
call _DV_IFS_len
add $4, %esp
mov %eax, -4(%ebp)

mov -24(%ebp), %eax	# Move var1_a,R3
mov %eax, -12(%ebp)

mov -32(%ebp), %eax	# Move var2_b,R4
mov %eax, -16(%ebp)

# Library __stringCat(R3, R4),R3
mov -16(%ebp), %eax
push %eax
mov -12(%ebp), %eax
push %eax
call __stringCat
add $8, %esp
mov %eax, -12(%ebp)

# StaticCall _DV_IFS_len(param1_s=R3),R2
mov -12(%ebp), %eax
push %eax
call _DV_IFS_len
add $4, %esp
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JZ _true_label1

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label1

_true_label1:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label1:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label2

mov $str4, %eax	# Move str4,R3
mov %eax, -12(%ebp)

# Library __println(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __println
add $4, %esp

_end_label2:

mov $str3, %eax	# Move str3,R2
mov %eax, -8(%ebp)

# StaticCall _DV_IFS_len(param1_s=R2),R1
mov -8(%ebp), %eax
push %eax
call _DV_IFS_len
add $4, %esp
mov %eax, -4(%ebp)

mov $str3, %eax	# Move str3,R3
mov %eax, -12(%ebp)

# StaticCall _DV_IFS_len(param1_s=R3),R2
mov -12(%ebp), %eax
push %eax
call _DV_IFS_len
add $4, %esp
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JZ _true_label3

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label3

_true_label3:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label3:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label4

mov $str5, %eax	# Move str5,R3
mov %eax, -12(%ebp)

# Library __println(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __println
add $4, %esp

_end_label4:

mov -24(%ebp), %eax	# Move var1_a,R1
mov %eax, -4(%ebp)

mov -32(%ebp), %eax	# Move var2_b,R2
mov %eax, -8(%ebp)

# Library __stringCat(R1, R2),R1
mov -8(%ebp), %eax
push %eax
mov -4(%ebp), %eax
push %eax
call __stringCat
add $8, %esp
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var4_ab
mov %eax, -28(%ebp)

mov $str3, %eax	# Move str3,R2
mov %eax, -8(%ebp)

# StaticCall _DV_IFS_len(param1_s=R2),R1
mov -8(%ebp), %eax
push %eax
call _DV_IFS_len
add $4, %esp
mov %eax, -4(%ebp)

mov -28(%ebp), %eax	# Move var4_ab,R3
mov %eax, -12(%ebp)

# StaticCall _DV_IFS_len(param1_s=R3),R2
mov -12(%ebp), %eax
push %eax
call _DV_IFS_len
add $4, %esp
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JZ _true_label5

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label5

_true_label5:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label5:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label6

mov $str6, %eax	# Move str6,R3
mov %eax, -12(%ebp)

# Library __println(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __println
add $4, %esp

_end_label6:

mov -28(%ebp), %eax	# Move var4_ab,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var5_abab
mov %eax, -44(%ebp)

mov -28(%ebp), %eax	# Move var4_ab,R1
mov %eax, -4(%ebp)

mov -44(%ebp), %eax	# Move var5_abab,R2
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JZ _true_label7

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label7

_true_label7:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label7:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label8

mov $str7, %eax	# Move str7,R3
mov %eax, -12(%ebp)

# Library __println(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __println
add $4, %esp

_end_label8:

mov $str8, %eax	# Move str8,R2
mov %eax, -8(%ebp)

# StaticCall _DV_IFS_len(param1_s=R2),R1
mov -8(%ebp), %eax
push %eax
call _DV_IFS_len
add $4, %esp
mov %eax, -4(%ebp)

mov -24(%ebp), %eax	# Move var1_a,R3
mov %eax, -12(%ebp)

mov -36(%ebp), %eax	# Move var3_c,R4
mov %eax, -16(%ebp)

# Library __stringCat(R3, R4),R3
mov -16(%ebp), %eax
push %eax
mov -12(%ebp), %eax
push %eax
call __stringCat
add $8, %esp
mov %eax, -12(%ebp)

# StaticCall _DV_IFS_len(param1_s=R3),R2
mov -12(%ebp), %eax
push %eax
call _DV_IFS_len
add $4, %esp
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Compare R1,R2
cmp -4(%ebp), %eax

JZ _true_label9

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

JMP _end_label9

_true_label9:

mov $1, %eax	# Move 1,R1
mov %eax, -4(%ebp)

_end_label9:

mov -4(%ebp), %eax	# Compare 1,R1
cmp $1, %eax

JNZ _end_label10

mov $str9, %eax	# Move str9,R3
mov %eax, -12(%ebp)

# Library __println(R3),Rdummy
mov -12(%ebp), %eax
push %eax
call __println
add $4, %esp

_end_label10:

mov $0, %eax	# Move 0,R1
mov %eax, -4(%ebp)

mov -4(%ebp), %eax	# Move R1,var6_i
mov %eax, -40(%ebp)

_test_label11:

mov $3, %eax	# Move 3,R1
mov %eax, -4(%ebp)

mov -40(%ebp), %eax	# Move var6_i,R2
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

mov -24(%ebp), %eax	# Move var1_a,R2
mov %eax, -8(%ebp)

mov -24(%ebp), %eax	# Move var1_a,R3
mov %eax, -12(%ebp)

# Library __stringCat(R2, R3),R2
mov -12(%ebp), %eax
push %eax
mov -8(%ebp), %eax
push %eax
call __stringCat
add $8, %esp
mov %eax, -8(%ebp)

mov -8(%ebp), %eax	# Move R2,var1_a
mov %eax, -24(%ebp)

mov -40(%ebp), %eax	# Move var6_i,R4
mov %eax, -16(%ebp)

mov $1, %eax	# Move 1,R5
mov %eax, -20(%ebp)

mov -16(%ebp), %eax	# Add R5,R4
Add -20(%ebp), %eax
mov %eax, -16(%ebp)

mov -16(%ebp), %eax	# Move R4,var6_i
mov %eax, -40(%ebp)

JMP _test_label11

_end_label11:

mov -24(%ebp), %eax	# Move var1_a,R2
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
_DV_IFS_len:
push %ebp	# prologue
mov %esp, %ebp
sub $8, %esp

mov 8(%ebp), %eax	# Move param1_s,R2
mov %eax, -8(%ebp)

# Library __stoa(R2),R1
mov -8(%ebp), %eax
push %eax
call __stoa
add $4, %esp
mov %eax, -4(%ebp)
mov %eax, %ebx	# fixing stoa output length to be artificially multiblied by 4
mov -4(%ebx), %eax
mov $4, %ecx
mul %ecx
mov %eax, -4(%ebx)

mov -4(%ebp), %eax	# Compare 0,R1
cmp $0, %eax

JNZ _end_label13

mov $str10, %eax	# Move str10,R1
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

mov -4(%ebp), %eax	# Return R1
mov %ebp, %esp	# epilogue
pop %ebp
ret

