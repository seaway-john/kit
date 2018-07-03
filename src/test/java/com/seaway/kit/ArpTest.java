package com.seaway.kit;

import com.seaway.kit.protocol.Arp;
import org.junit.Test;

public class ArpTest {

    @Test
    public void test() {
        System.out.println(System.getProperty("java.library.path"));

        Arp arp = new Arp();
        arp.send();
    }

}
