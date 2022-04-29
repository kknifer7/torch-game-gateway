package xit.gateway.core.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.FileSystem;
import oshi.software.os.NetworkParams;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;
import xit.gateway.utils.OshiUtils;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TestOshi {
    @Test
    void test(){
        System.out.println(OshiUtils.getSimpleCPUInfo());
        System.out.println(OshiUtils.getSimpleMemoryInfo());
    }
}
