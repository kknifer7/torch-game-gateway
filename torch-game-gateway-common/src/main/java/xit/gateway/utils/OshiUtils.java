package xit.gateway.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.Util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class OshiUtils {
    private final static SystemInfo si;
    private final static HardwareAbstractionLayer hal;

    private OshiUtils(){}

    static{
        si = new SystemInfo();
        hal = si.getHardware();
    }

    public static SimpleMemoryInfo getSimpleMemoryInfo(){
        GlobalMemory memory = hal.getMemory();

        return new SimpleMemoryInfo(memory.getTotal(), memory.getAvailable());
    }

    public static SimpleCPUInfo getSimpleCPUInfo(){
        CentralProcessor processor = hal.getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

        DecimalFormat df = new DecimalFormat("0.00");

        df.setRoundingMode(RoundingMode.HALF_UP);
        double cpuUsed = Double.parseDouble(df.format(1.0 - (idle * 1.0 / totalCpu)));

        return new SimpleCPUInfo(processor.getLogicalProcessorCount(), cpuUsed);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    public static class SimpleMemoryInfo{
        private long total;
        private long available;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    public static class SimpleCPUInfo{
        private int logicalProcessorCount;
        private double cpuUsed;
    }
}
