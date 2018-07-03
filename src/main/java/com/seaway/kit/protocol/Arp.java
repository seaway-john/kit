package com.seaway.kit.protocol;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Arp {

    private Map<String, String> ipMacMap = new HashMap<>();

    public void send() {
        try {
            InetAddress destAddress = InetAddress.getByName("192.168.1.165");
            byte[] destMac = strToMac("11-11-11-11-11-11");

            InetAddress srcAddress = InetAddress.getByName("192.168.1.163");
            byte[] srcMac = strToMac("22-22-22-22-22-22");

            NetworkInterface[] devices = JpcapCaptor.getDeviceList();
            NetworkInterface device = devices[1];

            JpcapSender sender = JpcapSender.openDevice(device);

            ARPPacket arp = new ARPPacket();
            arp.hardtype = ARPPacket.HARDTYPE_ETHER;
            arp.prototype = ARPPacket.PROTOTYPE_IP;
            arp.operation = ARPPacket.ARP_REPLY;
            arp.hlen = 6;
            arp.plen = 4;
            arp.sender_hardaddr = srcMac;
            arp.sender_protoaddr = srcAddress.getAddress();
            arp.target_hardaddr = destMac;
            arp.target_protoaddr = destAddress.getAddress();

            EthernetPacket ethernet = new EthernetPacket();
            ethernet.frametype = EthernetPacket.ETHERTYPE_ARP;
            ethernet.src_mac = srcMac;
            ethernet.dst_mac = destMac;
            arp.datalink = ethernet;

            sender.sendPacket(arp);
        } catch (Exception e) {
            log.error("Exception in send, reason {}", e.getMessage());
        }

    }

    private byte[] strToMac(String strMac) {
        byte[] byteMacs = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        String[] strMacs = strMac.split("-");
        for (int x = 0; x < strMacs.length; x++) {
            byteMacs[x] = (byte) ((Integer.parseInt(strMacs[x], 16)) & 0xff);
        }

        return byteMacs;
    }

}
