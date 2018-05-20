package com.mmall.practice.example.jvm;

import lombok.extern.slf4j.Slf4j;

/*
~  ᐅ jps
96657 SearchBusiestCPU
96656 Launcher
96807 Jps
94666

~  ᐅ top -Hp 95824
max下效果差点

~  ᐅ top -pid 95824
Processes: 406 total, 3 running, 403 sleeping, 1795 threads                                                                                                                                                                          12:26:07
Load Avg: 2.54, 3.33, 3.87  CPU usage: 33.80% user, 7.14% sys, 59.4% idle   SharedLibs: 299M resident, 59M data, 75M linkedit. MemRegions: 113287 total, 4677M resident, 134M private, 1229M shared.
PhysMem: 13G used (2454M wired), 2663M unused. VM: 1909G vsize, 1115M framework vsize, 9430895(0) swapins, 9609465(0) swapouts. Networks: packets: 3357327/2428M in, 2895209/655M out. Disks: 2372311/75G read, 2471489/84G written.

PID    COMMAND      %CPU TIME     #TH  #WQ  #POR MEM  PURG CMPR PGRP  PPID  STATE   BOOSTS     %CPU_ME %CPU_OTHRS UID  FAULTS COW  MSGS MSGR SYSBSD SYSM CSW    PAGE IDLE POWE INSTRS     CYCLES     USER  #MRE RPRV VPRV VSIZ KPRV KSHR
96657  java         95.3 00:37.29 30/1 1    98   36M  0B   0B   94666 94666 running *0[1]      0.00000 0.00000    501  15740  380  162  57   12455+ 654  38969+ 0    0    95.3 2594789384 3136926763 jimin N/A  N/A  N/A  N/A  N/A  N/A

~  ᐅ jstack 96657
2018-05-20 12:25:46
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.92-b14 mixed mode):

"Attach Listener" #22 daemon prio=9 os_prio=31 tid=0x00007fcca2808800 nid=0x4c07 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"DestroyJavaVM" #21 prio=5 os_prio=31 tid=0x00007fcca32a0000 nid=0x1903 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Buiest thread" #20 prio=5 os_prio=31 tid=0x00007fcca21ae800 nid=0x5b03 runnable [0x000070000fa38000]
   java.lang.Thread.State: RUNNABLE
	at com.mmall.practice.example.jvm.SearchBusiestCPU$2.run(SearchBusiestCPU.java:123)

"Thread-9" #19 prio=5 os_prio=31 tid=0x00007fcca2a10000 nid=0x5a03 waiting on condition [0x000070000f935000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(Native Method)
	at com.mmall.practice.example.jvm.SearchBusiestCPU$1.run(SearchBusiestCPU.java:112)

"Thread-8" #18 prio=5 os_prio=31 tid=0x00007fcca21ae000 nid=0x5903 waiting on condition [0x000070000f832000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(Native Method)
	at com.mmall.practice.example.jvm.SearchBusiestCPU$1.run(SearchBusiestCPU.java:112)

"Thread-7" #17 prio=5 os_prio=31 tid=0x00007fcca2a0f000 nid=0xa503 waiting on condition [0x000070000f72f000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(Native Method)
	at com.mmall.practice.example.jvm.SearchBusiestCPU$1.run(SearchBusiestCPU.java:112)

"Thread-6" #16 prio=5 os_prio=31 tid=0x00007fcca21ad000 nid=0xa703 waiting on condition [0x000070000f62c000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(Native Method)
	at com.mmall.practice.example.jvm.SearchBusiestCPU$1.run(SearchBusiestCPU.java:112)

"Thread-5" #15 prio=5 os_prio=31 tid=0x00007fcca2a0e800 nid=0xa803 waiting on condition [0x000070000f529000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(Native Method)
	at com.mmall.practice.example.jvm.SearchBusiestCPU$1.run(SearchBusiestCPU.java:112)

"Thread-4" #14 prio=5 os_prio=31 tid=0x00007fcca21ac800 nid=0x5603 waiting on condition [0x000070000f426000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(Native Method)
	at com.mmall.practice.example.jvm.SearchBusiestCPU$1.run(SearchBusiestCPU.java:112)

"Thread-3" #13 prio=5 os_prio=31 tid=0x00007fcca2997000 nid=0x3d03 waiting on condition [0x000070000f323000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(Native Method)
	at com.mmall.practice.example.jvm.SearchBusiestCPU$1.run(SearchBusiestCPU.java:112)

"Thread-2" #12 prio=5 os_prio=31 tid=0x00007fcca210b800 nid=0x3e03 waiting on condition [0x000070000f220000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(Native Method)
	at com.mmall.practice.example.jvm.SearchBusiestCPU$1.run(SearchBusiestCPU.java:112)

"Thread-1" #11 prio=5 os_prio=31 tid=0x00007fcca212b800 nid=0x3f03 waiting on condition [0x000070000f11d000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(Native Method)
	at com.mmall.practice.example.jvm.SearchBusiestCPU$1.run(SearchBusiestCPU.java:112)

"Thread-0" #10 prio=5 os_prio=31 tid=0x00007fcca2107800 nid=0x4103 waiting on condition [0x000070000f01a000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(Native Method)
	at com.mmall.practice.example.jvm.SearchBusiestCPU$1.run(SearchBusiestCPU.java:112)

"Service Thread" #9 daemon prio=9 os_prio=31 tid=0x00007fcca3a96000 nid=0x3903 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C1 CompilerThread2" #8 daemon prio=9 os_prio=31 tid=0x00007fcca31ff800 nid=0x4403 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread1" #7 daemon prio=9 os_prio=31 tid=0x00007fcca3031800 nid=0x3703 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread0" #6 daemon prio=9 os_prio=31 tid=0x00007fcca2975800 nid=0x4703 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Monitor Ctrl-Break" #5 daemon prio=5 os_prio=31 tid=0x00007fcca20e9000 nid=0x3503 runnable [0x000070000ea08000]
   java.lang.Thread.State: RUNNABLE
	at java.net.SocketInputStream.socketRead0(Native Method)
	at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
	at java.net.SocketInputStream.read(SocketInputStream.java:170)
	at java.net.SocketInputStream.read(SocketInputStream.java:141)
	at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
	at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
	at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
	- locked <0x000000076af70c28> (a java.io.InputStreamReader)
	at java.io.InputStreamReader.read(InputStreamReader.java:184)
	at java.io.BufferedReader.fill(BufferedReader.java:161)
	at java.io.BufferedReader.readLine(BufferedReader.java:324)
	- locked <0x000000076af70c28> (a java.io.InputStreamReader)
	at java.io.BufferedReader.readLine(BufferedReader.java:389)
	at com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:63)

"Signal Dispatcher" #4 daemon prio=9 os_prio=31 tid=0x00007fcca3036800 nid=0x490b runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" #3 daemon prio=8 os_prio=31 tid=0x00007fcca300f000 nid=0x2f03 in Object.wait() [0x000070000e77f000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x000000076ab08ee0> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
	- locked <0x000000076ab08ee0> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
	at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)

"Reference Handler" #2 daemon prio=10 os_prio=31 tid=0x00007fcca300e000 nid=0x5303 in Object.wait() [0x000070000e67c000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x000000076ab06b50> (a java.lang.ref.Reference$Lock)
	at java.lang.Object.wait(Object.java:502)
	at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
	- locked <0x000000076ab06b50> (a java.lang.ref.Reference$Lock)
	at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

"VM Thread" os_prio=31 tid=0x00007fcca3854800 nid=0x2d03 runnable

"GC task thread#0 (ParallelGC)" os_prio=31 tid=0x00007fcca200f800 nid=0x1e07 runnable

"GC task thread#1 (ParallelGC)" os_prio=31 tid=0x00007fcca3007800 nid=0x2103 runnable

"GC task thread#2 (ParallelGC)" os_prio=31 tid=0x00007fcca2010000 nid=0x2a03 runnable

"GC task thread#3 (ParallelGC)" os_prio=31 tid=0x00007fcca2011000 nid=0x2b03 runnable

"VM Periodic Task Thread" os_prio=31 tid=0x00007fcca3a97800 nid=0x3a03 waiting on condition

JNI global references: 62

~  ᐅ




 */
@Slf4j
public class SearchBusiestCPU {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(100000);
                    } catch (Exception e) {
                        log.error("exception", e);
                    }
                }
            }.start();
        }
        Thread t = new Thread() {
            public void run() {
                int i = 0;
                while (true) {
                    i = (i++) / 100;
                }
            }
        };
        t.setName("Buiest thread");
        t.start();
    }
}

