package com.ebinumer.usbprinter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends Activity {

    private UsbManager mUsbManager;
    private UsbDevice mDevice;
    private UsbDeviceConnection mConnection;
    private UsbInterface mInterface;
    private UsbEndpoint mEndPoint;
    private PendingIntent mPermissionIntent;
    EditText ed_txt;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private static Boolean forceCLaim = true;

    HashMap<String, UsbDevice> mDeviceList;
    Iterator<UsbDevice> mDeviceIterator;
    byte[] testBytes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ed_txt = (EditText) findViewById(R.id.ed_txt);
        Button print = (Button) findViewById(R.id.print);
        Button connect = (Button) findViewById(R.id.connect);
        Button lib_connect = (Button) findViewById(R.id.librery_connect);

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        mDeviceList = mUsbManager.getDeviceList();
        Toast.makeText(this, "Device List Size: " + String.valueOf(mDeviceList.size()), Toast.LENGTH_SHORT).show();
        if (mDeviceList.size() > 0) {
            mDeviceIterator = mDeviceList.values().iterator();

            Toast.makeText(this, "Device List Size: " + String.valueOf(mDeviceList.size()), Toast.LENGTH_SHORT).show();

            TextView textView = (TextView) findViewById(R.id.usbDevice);
            String usbDevice = "";
            while (mDeviceIterator.hasNext()) {
                UsbDevice usbDevice1 = mDeviceIterator.next();
                if(usbDevice1.getManufacturerName().equals("EPSON")) {
                    usbDevice += "\n" +
                            "DeviceID: " + usbDevice1.getDeviceId() + "\n" +
                            "DeviceName: " + usbDevice1.getDeviceName() + "\n" +
                            "Protocol: " + usbDevice1.getDeviceProtocol() + "\n" +
                            "Product Name: " + usbDevice1.getProductName() + "\n" +
                            "Manufacturer Name: " + usbDevice1.getManufacturerName() + "\n" +
                            "DeviceClass: " + usbDevice1.getDeviceClass() + " - " + translateDeviceClass(usbDevice1.getDeviceClass()) + "\n" +
                            "DeviceSubClass: " + usbDevice1.getDeviceSubclass() + "\n" +
                            "VendorID: " + usbDevice1.getVendorId() + "\n" +
                            "ProductID: " + usbDevice1.getProductId() + "\n";
                }
                int interfaceCount = usbDevice1.getInterfaceCount();
                Toast.makeText(this, "INTERFACE COUNT: " + String.valueOf(interfaceCount), Toast.LENGTH_SHORT).show();

                mDevice = usbDevice1;

                Toast.makeText(this, "Device is attached", Toast.LENGTH_SHORT).show();
                textView.setText(usbDevice);
            }

            mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
            IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
            registerReceiver(mUsbReceiver, filter);

            mUsbManager.requestPermission(mDevice, mPermissionIntent);
        } else {
            Toast.makeText(this, "Please attach printer via USB", Toast.LENGTH_SHORT).show();
        }
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print(mConnection, mInterface);
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
                mDeviceList = mUsbManager.getDeviceList();
                Toast.makeText(MainActivity.this, "Device List Size: " + String.valueOf(mDeviceList.size()), Toast.LENGTH_SHORT).show();
                if (mDeviceList.size() > 0) {

                    mDeviceIterator = mDeviceList.values().iterator();

                    Toast.makeText(MainActivity.this, "Device List Size: " + String.valueOf(mDeviceList.size()), Toast.LENGTH_SHORT).show();

                    TextView textView = (TextView) findViewById(R.id.usbDevice);
                    String usbDevice = "";
                    while (mDeviceIterator.hasNext()) {
                        UsbDevice usbDevice1 = mDeviceIterator.next();
                        if(usbDevice1.getManufacturerName().equals("EPSON")) {
                            usbDevice += "\n" +
                                    "DeviceID: " + usbDevice1.getDeviceId() + "\n" +
                                    "DeviceName: " + usbDevice1.getDeviceName() + "\n" +
                                    "Protocol: " + usbDevice1.getDeviceProtocol() + "\n" +
                                    "Product Name: " + usbDevice1.getProductName() + "\n" +
                                    "Manufacturer Name: " + usbDevice1.getManufacturerName() + "\n" +
                                    "DeviceClass: " + usbDevice1.getDeviceClass() + " - " + translateDeviceClass(usbDevice1.getDeviceClass()) + "\n" +
                                    "DeviceSubClass: " + usbDevice1.getDeviceSubclass() + "\n" +
                                    "VendorID: " + usbDevice1.getVendorId() + "\n" +
                                    "ProductID: " + usbDevice1.getProductId() + "\n";
                        }
                        int interfaceCount = usbDevice1.getInterfaceCount();
                        Toast.makeText(MainActivity.this, "INTERFACE COUNT: " + String.valueOf(interfaceCount), Toast.LENGTH_SHORT).show();

                        mDevice = usbDevice1;

                        Toast.makeText(MainActivity.this, "Device is attached", Toast.LENGTH_SHORT).show();
                        textView.setText(usbDevice);
                    }

                    mPermissionIntent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(ACTION_USB_PERMISSION), 0);
                    IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
                    registerReceiver(mUsbReceiver, filter);

                    mUsbManager.requestPermission(mDevice, mPermissionIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Please attach printer via USB", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void print(final UsbDeviceConnection connection, final UsbInterface usbInterface) {
        final String test = ed_txt.getText().toString() + "\n\n";
        testBytes = test.getBytes();




        if (usbInterface == null) {
            Toast.makeText(this, "INTERFACE IS NULL1", Toast.LENGTH_SHORT).show();
        } else if (connection == null) {
            Toast.makeText(this, "CONNECTION IS NULL2", Toast.LENGTH_SHORT).show();
        } else if (forceCLaim == null) {
            Toast.makeText(this, "FORCE CLAIM IS NULL3", Toast.LENGTH_SHORT).show();
        } else {

            connection.claimInterface(usbInterface, forceCLaim);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    byte[] cut_paper = PrinterCommands.FEED_PAPER_AND_CUT1;
                    connection.bulkTransfer(mEndPoint, testBytes, testBytes.length, 0);
                    connection.bulkTransfer(mEndPoint, cut_paper, cut_paper.length, 0);
                }
            });
            thread.run();
        }
    }


    final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            //call method to set up device communication
                            mInterface = device.getInterface(0);
                            mEndPoint = mInterface.getEndpoint(0);// 0 IN and  1 OUT to printer.
                            mConnection = mUsbManager.openDevice(device);

                        }
                    } else {
                        Toast.makeText(context, "PERMISSION DENIED FOR THIS DEVICE", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };


    private String translateDeviceClass(int deviceClass) {

        switch (deviceClass) {

            case UsbConstants.USB_CLASS_APP_SPEC:
                return "Application specific USB class";

            case UsbConstants.USB_CLASS_AUDIO:
                return "USB class for audio devices";

            case UsbConstants.USB_CLASS_CDC_DATA:
                return "USB class for CDC devices (communications device class)";

            case UsbConstants.USB_CLASS_COMM:
                return "USB class for communication devices";

            case UsbConstants.USB_CLASS_CONTENT_SEC:
                return "USB class for content security devices";

            case UsbConstants.USB_CLASS_CSCID:
                return "USB class for content smart card devices";

            case UsbConstants.USB_CLASS_HID:
                return "USB class for human interface devices (for example, mice and keyboards)";

            case UsbConstants.USB_CLASS_HUB:
                return "USB class for USB hubs";

            case UsbConstants.USB_CLASS_MASS_STORAGE:
                return "USB class for mass storage devices";

            case UsbConstants.USB_CLASS_MISC:
                return "USB class for wireless miscellaneous devices";

            case UsbConstants.USB_CLASS_PER_INTERFACE:
                return "USB class indicating that the class is determined on a per-interface basis";

            case UsbConstants.USB_CLASS_PHYSICA:
                return "USB class for physical devices";

            case UsbConstants.USB_CLASS_PRINTER:
                return "USB class for printers";

            case UsbConstants.USB_CLASS_STILL_IMAGE:
                return "USB class for still image devices (digital cameras)";

            case UsbConstants.USB_CLASS_VENDOR_SPEC:
                return "Vendor specific USB class";

            case UsbConstants.USB_CLASS_VIDEO:
                return "USB class for video devices";

            case UsbConstants.USB_CLASS_WIRELESS_CONTROLLER:
                return "USB class for wireless controller devices";

            default:
                return "Unknown USB class!";
        }
    }

}
