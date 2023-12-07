package test.brother.com.brother_test_program;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.brotherdll.BrotherActivity;
import com.example.brotherdll.BrotherUSBActivity;
import com.example.brotherdll.BrotherWifiActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private TextView mTextIP;
    private EditText mEditIP;
    private TextView mTextPDF_Name;
    private TextView mTextPDF2;
    private EditText mEditPDF;
    private TextView mTextRFID2;
    private EditText mEditRFID;

    private Button mButtonLabel;
    private Button mButtonPDF;
    private Button mButtonPDF_Load;
    private Button mButtonRFID;

    private TextView mTextImg_Name;
    private EditText mEditImgW;
    private EditText mEditImgH;

    private Button mButtonImg;
    private Button mButtonImg_Load;


    private int CurrentPage = 1;

    BrotherWifiActivity BrotherDll_wifi = new BrotherWifiActivity();
    BrotherActivity BrotherDll_BT = new BrotherActivity();
    BrotherUSBActivity BrotherDll_USB = new BrotherUSBActivity();

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private static UsbManager mUsbManager;
    private static PendingIntent mPermissionIntent;
    private static boolean hasPermissionToCommunicate = false;
    private static UsbDevice device;

    private static final int FILE_OPEN_PDF = 2;
    private static final int FILE_OPEN_IMG = 3;
    private static int PrinterDPI = 203;
    private static int TotalPage = 0;

    private static String PDFName = "multipdf.pdf";
    private static File file_PDF;

    //private static String TTFName = "ARIAL.TTF";
    //private File file_TTF;

    private static String PCXName = "UL.PCX";
    private File file_PCX;

    private static String BMPName = "LOGO.BMP";
    private File file_BMP;

    /*private static int paper_width = 100;
    private static int paper_height = 60;
    private static int speed = 4;
    private static int density = 15;
    private static int sensor = 0;
    private static int sensor_distance = 0;
    private static int sensor_offset = 0;*/

    private static Uri PDFURI;

    private static Uri ImgURI;

    private static String TMP_IP = "10.0.10.202";
    private static String TMP_MAC = "8C:DE:52:9B:E4:3A";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if (CurrentPage == 1) TMP_IP = mEditIP.getText().toString();
            else if (CurrentPage == 2) TMP_MAC = mEditIP.getText().toString();

            switch (item.getItemId()) {
                case R.id.navigation_NET:
                    mTextMessage.setText(R.string.title_NET);
                    mTextIP.setVisibility(View.VISIBLE);
                    mTextIP.setText("IP Address");
                    mEditIP.setVisibility(View.VISIBLE);
                    mEditIP.setText(TMP_IP);
                    mEditIP.clearFocus();
                    CurrentPage = 1;
                    return true;
                case R.id.navigation_BT:
                    mTextMessage.setText(R.string.title_BT);
                    mTextIP.setVisibility(View.VISIBLE);
                    mTextIP.setText("BT MAC Address");
                    mEditIP.setVisibility(View.VISIBLE);
                    mEditIP.setText(TMP_MAC);
                    mEditIP.clearFocus();
                    CurrentPage = 2;
                    return true;
                case R.id.navigation_USB:
                    mTextMessage.setText(R.string.title_USB);
                    mTextIP.setVisibility(View.INVISIBLE);
                    mEditIP.setVisibility(View.INVISIBLE);
                    CurrentPage = 3;
                    return true;
            }
            return false;
        }
    };

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            hasPermissionToCommunicate = true;
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);

        mTextIP = (TextView) findViewById(R.id.textView_IP);
        mEditIP = (EditText) findViewById(R.id.et_IP);

        mTextPDF_Name = (TextView) findViewById(R.id.textView_PDF_Name2);
        mTextPDF2 = (TextView) findViewById(R.id.textView_PDF_Page2);
        mEditPDF = (EditText) findViewById(R.id.et_Page);

        mTextRFID2 = (TextView) findViewById(R.id.textView_RFID_Received);
        mEditRFID = (EditText) findViewById(R.id.et_RFID);

        mButtonLabel = (Button) findViewById(R.id.button_Label);
        mButtonPDF_Load = (Button) findViewById(R.id.button_Load);
        mButtonPDF = (Button) findViewById(R.id.button_PDF);
        mButtonRFID = (Button) findViewById(R.id.button_RFID);

        mTextImg_Name = (TextView) findViewById(R.id.textView_Img_Name2);
        mEditImgW = (EditText) findViewById(R.id.et_ImgW);
        mEditImgH = (EditText) findViewById(R.id.et_ImgH);

        mButtonImg_Load = (Button) findViewById(R.id.button_LoadImg);
        mButtonImg = (Button) findViewById(R.id.button_Img);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        mPermissionIntent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(ACTION_USB_PERMISSION), PendingIntent.FLAG_IMMUTABLE); // Remember 0 to change PendingIntent.FLAG_IMMUTABLE
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);//request permission

        file_PDF = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + PDFName);
        file_PCX = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + PCXName);
        file_BMP = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + BMPName);

        try {
            //region Copy pdf from assets to "Download" folder if file not exist
            if (!file_PDF.exists()) {
                AssetManager am = getAssets();
                InputStream input = am.open(PDFName);
                try {
                    try (OutputStream output = new FileOutputStream(file_PDF)) {
                        byte[] buffer = new byte[4 * 1024]; // or other buffer size
                        int read;

                        while ((read = input.read(buffer)) != -1) {
                            output.write(buffer, 0, read);
                        }

                        output.flush();
                    }
                } finally {
                    input.close();
                }
            }
            //endregion

            //region Copy UL.PCX from assets to "Download" folder if file not exist
            if (!file_PCX.exists()) {
                AssetManager am = getAssets();
                InputStream input = am.open(PCXName);
                try {
                    try (OutputStream output = new FileOutputStream(file_PCX)) {
                        byte[] buffer = new byte[4 * 1024]; // or other buffer size
                        int read;

                        while ((read = input.read(buffer)) != -1) {
                            output.write(buffer, 0, read);
                        }

                        output.flush();
                    }
                } finally {
                    input.close();
                }
            }
            //endregion

            //region Copy LOGO.BMP from assets to "Download" folder if file not exist
            if (!file_BMP.exists()) {
                AssetManager am = getAssets();
                InputStream input = am.open(BMPName);
                try {
                    try (OutputStream output = new FileOutputStream(file_BMP)) {
                        byte[] buffer = new byte[4 * 1024]; // or other buffer size
                        int read;

                        while ((read = input.read(buffer)) != -1) {
                            output.write(buffer, 0, read);
                        }

                        output.flush();
                    }
                } finally {
                    input.close();
                }
            }
            //endregion
        } catch (Exception ex) {

        }


        mButtonLabel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                switch (CurrentPage) {
                    case 1:
                        Label_NET();
                        break;
                    case 2:
                        Label_BT();
                        break;
                    case 3:
                        Label_USB();
                        break;
                }
            }
        });

        mButtonPDF_Load.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                startActivityForResult(intent, FILE_OPEN_PDF);
            }
        });

        mButtonPDF.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                switch (CurrentPage) {
                    case 1:
                        PDF_NET();
                        break;
                    case 2:
                        PDF_BT();
                        break;
                    case 3:
                        PDF_USB();
                        break;
                }
            }
        });

        mButtonRFID.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                switch (CurrentPage) {
                    case 1:
                        RFID_NET();
                        break;
                    case 2:
                        RFID_BT();
                        break;
                    case 3:
                        RFID_USB();
                        break;
                }
            }
        });

        mButtonImg_Load.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, FILE_OPEN_IMG);
            }
        });

        mButtonImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                switch (CurrentPage) {
                    case 1:
                        IMG_NET();
                        break;
                    case 2:
                        IMG_BT();
                        break;
                    case 3:
                        IMG_USB();
                        break;
                }
            }
        });

    }

    void Label_NET() {
        try {
            BrotherDll_wifi.openport(mEditIP.getText().toString(), 9100);
            BrotherDll_wifi.downloadpcx(this, Uri.fromFile(file_PCX), PCXName);
            BrotherDll_wifi.downloadbmp(this, Uri.fromFile(file_BMP), BMPName);

            BrotherDll_wifi.setup(50, 50, 2, 15, 0, 0, 0);
            BrotherDll_wifi.clearbuffer();
            BrotherDll_wifi.nobackfeed();
            BrotherDll_wifi.barcode(10, 10, "128", 40, 1, 0, 2, 2, "1234567");
            //BrotherDll_wifi.windowsfont(10, 100, 24, path_TTF, "windowsfont");
            BrotherDll_wifi.printerfont(10, 150, "0", 0, 10, 10, "printerfont");
            BrotherDll_wifi.sendcommand("PUTPCX 145,15,\"UL.PCX\"\r\n");
            BrotherDll_wifi.sendcommand("PUTBMP 10,190,\"LOGO.BMP\"\r\n");

            BrotherDll_wifi.printlabel(1, 1);
            BrotherDll_wifi.sendcommand("FEED 200\r\n");
            //BrotherDll_wifi.sendfile(this,Uri.fromFile(file_TXT));
            Log.v("tsd_dll_test", BrotherDll_wifi.printerstatus(1000));
            BrotherDll_wifi.formfeed();
            BrotherDll_wifi.closeport(2000);
        } catch (Exception ex) {

        }
    }

    void Label_BT() {
        try {

            BrotherDll_BT.openport(mEditIP.getText().toString());
            BrotherDll_BT.downloadpcx(this, Uri.fromFile(file_PCX), PCXName);
            BrotherDll_BT.downloadbmp(this, Uri.fromFile(file_BMP), BMPName);

            BrotherDll_BT.setup(50, 50, 2, 15, 0, 0, 0);
            BrotherDll_BT.clearbuffer();
            BrotherDll_BT.nobackfeed();
            BrotherDll_BT.barcode(10, 10, "128", 40, 1, 0, 2, 2, "1234567");
            //BrotherDll_BT.windowsfont(10, 100, 24, path_TTF, "windowsfont");
            BrotherDll_BT.printerfont(10, 150, "0", 0, 10, 10, "printerfont");
            BrotherDll_BT.sendcommand("PUTPCX 145,15,\"UL.PCX\"\r\n");
            BrotherDll_BT.sendcommand("PUTBMP 10,190,\"LOGO.BMP\"\r\n");

            BrotherDll_BT.printlabel(1, 1);
            BrotherDll_BT.sendcommand("FEED 200\r\n");
            //BrotherDll_BT.sendfile(this,Uri.fromFile(file_TXT));
            Log.v("tsd_dll_test", BrotherDll_BT.printerstatus(1000));
            BrotherDll_BT.formfeed();
            BrotherDll_BT.closeport(2000);
        } catch (Exception ex) {

        }
    }

    void Label_USB() {
        try {

            HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();

            if (deviceList.size() == 0) {
                new AlertDialog.Builder(MainActivity.this).setTitle("Warning").setMessage("Please connect to USB device").show();
            } else {
                Log.d("Detect ", deviceList.size() + " USB device(s) found");
                Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
                Boolean HasBrother = false;
                while (deviceIterator.hasNext()) {
                    device = deviceIterator.next();
                    if (device.getVendorId() == 1273) {
                        HasBrother = true;
                        //Toast.makeText(MainActivity.this, device.toString(), 0).show();
                        break;
                    }
                }

                if (!HasBrother) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("Warning").setMessage("Please connect to USB device").show();
                } else {
                    if (!mUsbManager.hasPermission(device))
                        mUsbManager.requestPermission(device, mPermissionIntent);

                    if (mUsbManager.hasPermission(device)) {
                        BrotherDll_USB.openport(mUsbManager, device);

                        BrotherDll_USB.downloadpcx(this, Uri.fromFile(file_PCX), PCXName);
                        BrotherDll_USB.downloadbmp(this, Uri.fromFile(file_BMP), BMPName);

                        BrotherDll_USB.setup(50, 50, 2, 15, 0, 0, 0);
                        BrotherDll_USB.clearbuffer();
                        BrotherDll_USB.nobackfeed();
                        BrotherDll_USB.barcode(10, 10, "128", 40, 1, 0, 2, 2, "1234567");
                        //BrotherDll_USB.windowsfont(10, 100, 24, path_TTF, "windowsfont");
                        BrotherDll_USB.printerfont(10, 150, "0", 0, 10, 10, "printerfont");
                        BrotherDll_USB.sendcommand("PUTPCX 145,15,\"UL.PCX\"\r\n");
                        BrotherDll_USB.sendcommand("PUTBMP 10,190,\"LOGO.BMP\"\r\n");

                        BrotherDll_USB.printlabel(1, 1);
                        BrotherDll_USB.sendcommand("FEED 200\r\n");
                        //BrotherDll_BT.sendfile(this,Uri.fromFile(file_TXT));
                        Log.v("tsd_dll_test", BrotherDll_USB.printerstatus(1000));
                        BrotherDll_USB.formfeed();
                        BrotherDll_USB.closeport(2000);


                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    void PDF_NET() {
        try {
            BrotherDll_wifi.openport(mEditIP.getText().toString(), 9100);
            //BrotherDll_wifi.setup(paper_width,paper_height,speed,density,sensor,sensor_distance,sensor_offset);
            BrotherDll_wifi.clearbuffer();
            if (mEditPDF.getText().toString().equals("0")) {
                BrotherDll_wifi.printPDFbyPath(this, PDFURI, 0, 0, PrinterDPI);
            } else {
                int Page = Integer.parseInt(mEditPDF.getText().toString());
                if (Page >= 1 && Page <= TotalPage) {
                    BrotherDll_wifi.printPDFbyPath(this, PDFURI, 0, 0, PrinterDPI, Page);
                }
            }
            BrotherDll_wifi.closeport(500 * TotalPage);
        } catch (Exception ex) {

        }
    }

    void PDF_BT() {
        try {
            BrotherDll_BT.openport(mEditIP.getText().toString());
            //BrotherDll_BT.setup(paper_width,paper_height,speed,density,sensor,sensor_distance,sensor_offset);
            BrotherDll_BT.clearbuffer();
            if (mEditPDF.getText().toString().equals("0")) {
                BrotherDll_BT.printPDFbyPath(this, PDFURI, 0, 0, PrinterDPI);
            } else {
                int Page = Integer.parseInt(mEditPDF.getText().toString());
                if (Page >= 1 && Page <= TotalPage) {
                    BrotherDll_BT.printPDFbyPath(this, PDFURI, 0, 0, PrinterDPI, Page);
                }
            }
            BrotherDll_BT.closeport(7000 * TotalPage);
        } catch (Exception ex) {

        }
    }

    void PDF_USB() {
        try {
            HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();

            if (deviceList.size() == 0) {
                new AlertDialog.Builder(MainActivity.this).setTitle("Warning").setMessage("Please connect to USB device").show();
            } else {
                Log.d("Detect ", deviceList.size() + " USB device(s) found");
                Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
                Boolean HasBrother = false;
                while (deviceIterator.hasNext()) {
                    device = deviceIterator.next();
                    if (device.getVendorId() == 1273) {
                        HasBrother = true;
                        //Toast.makeText(MainActivity.this, device.toString(), 0).show();
                        break;
                    }
                }

                if (!HasBrother) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("Warning").setMessage("Please connect to USB device").show();
                } else {
                    if (!mUsbManager.hasPermission(device))
                        mUsbManager.requestPermission(device, mPermissionIntent);

                    if (mUsbManager.hasPermission(device)) {
                        BrotherDll_USB.openport(mUsbManager, device);
                        //BrotherDll_USB.setup(paper_width,paper_height,speed,density,sensor,sensor_distance,sensor_offset);
                        BrotherDll_USB.clearbuffer();
                        if (mEditPDF.getText().toString().equals("0")) {
                            BrotherDll_USB.printPDFbyPath(this, PDFURI, 0, 0, PrinterDPI);
                        } else {
                            int Page = Integer.parseInt(mEditPDF.getText().toString());
                            if (Page >= 1 && Page <= TotalPage) {
                                BrotherDll_USB.printPDFbyPath(this, PDFURI, 0, 0, PrinterDPI, Page);
                            }
                        }

                        BrotherDll_USB.closeport(500 * TotalPage);


                    }
                }
            }
        } catch (Exception ex) {

        }

    }

    void RFID_NET() {
        try {
            BrotherDll_wifi.openport(mEditIP.getText().toString(), 9100);
            BrotherDll_wifi.clearbuffer();
            BrotherDll_wifi.rfidWrite("0", "H", "0", "12", "EPC", mEditRFID.getText().toString());
            BrotherDll_wifi.rfidRead("0", "H", "0", "12", "EPC");

            BrotherDll_wifi.printlabel(1, 1);

            String recieved_RFID = BrotherDll_wifi.rfidGetReadData().replace("\r\n", "");
            mTextRFID2.setText(recieved_RFID);
            BrotherDll_wifi.closeport(5000);
        } catch (Exception ex) {

        }
    }

    void RFID_BT() {
        try {
            BrotherDll_BT.openport(mEditIP.getText().toString());
            BrotherDll_BT.clearbuffer();
            BrotherDll_BT.rfidWrite("0", "H", "0", "12", "EPC", mEditRFID.getText().toString());
            BrotherDll_BT.rfidRead("0", "H", "0", "12", "EPC");

            BrotherDll_BT.printlabel(1, 1);

            String recieved_RFID = BrotherDll_BT.rfidGetReadData().replace("\r\n", "");
            mTextRFID2.setText(recieved_RFID);
            BrotherDll_BT.closeport(5000);
        } catch (Exception ex) {

        }
    }

    void RFID_USB() {
        try {
            HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();

            if (deviceList.size() == 0) {
                new AlertDialog.Builder(MainActivity.this).setTitle("Warning").setMessage("Please connect to USB device").show();
            } else {
                Log.d("Detect ", deviceList.size() + " USB device(s) found");
                Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
                Boolean HasBrother = false;
                while (deviceIterator.hasNext()) {
                    device = deviceIterator.next();
                    if (device.getVendorId() == 1273) {
                        HasBrother = true;
                        //Toast.makeText(MainActivity.this, device.toString(), 0).show();
                        break;
                    }
                }

                if (!HasBrother) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("Warning").setMessage("Please connect to USB device").show();
                } else {
                    if (!mUsbManager.hasPermission(device))
                        mUsbManager.requestPermission(device, mPermissionIntent);

                    if (mUsbManager.hasPermission(device)) {
                        BrotherDll_USB.openport(mUsbManager, device);
                        BrotherDll_USB.clearbuffer();
                        BrotherDll_USB.rfidWrite("0", "H", "0", "12", "EPC", mEditRFID.getText().toString());
                        BrotherDll_USB.rfidRead("0", "H", "0", "12", "EPC");

                        BrotherDll_USB.printlabel(1, 1);
                        Thread.sleep(1000);
                        String recieved_RFID = BrotherDll_USB.rfidGetReadData().trim().replace("\r\n", "");
                        mTextRFID2.setText(recieved_RFID);
                        BrotherDll_USB.closeport(1000);
                    }
                }
            }
        } catch (Exception ex) {

        }

    }

    void IMG_NET() {
        try {
            BrotherDll_wifi.openport(mEditIP.getText().toString(), 9100);
            BrotherDll_wifi.clearbuffer();
            BrotherDll_wifi.sendImagebyPath(this, ImgURI, 0, 0, Integer.parseInt(mEditImgW.getText().toString()), Integer.parseInt(mEditImgH.getText().toString()), 64);
            BrotherDll_wifi.printlabel(1, 1);
            BrotherDll_wifi.closeport(5000);
        } catch (Exception ex) {

        }
    }

    void IMG_BT() {
        try {
            BrotherDll_BT.openport(mEditIP.getText().toString());
            BrotherDll_BT.clearbuffer();
            BrotherDll_BT.sendImagebyPath(this, ImgURI, 0, 0, Integer.parseInt(mEditImgW.getText().toString()), Integer.parseInt(mEditImgH.getText().toString()), 128);
            BrotherDll_BT.printlabel(1, 1);
            BrotherDll_BT.closeport(5000);
        } catch (Exception ex) {

        }
    }

    void IMG_USB() {
        try {
            HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();

            if (deviceList.size() == 0) {
                new AlertDialog.Builder(MainActivity.this).setTitle("Warning").setMessage("Please connect to USB device").show();
            } else {
                Log.d("Detect ", deviceList.size() + " USB device(s) found");
                Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
                Boolean HasBrother = false;
                while (deviceIterator.hasNext()) {
                    device = deviceIterator.next();
                    if (device.getVendorId() == 1273) {
                        HasBrother = true;
                        //Toast.makeText(MainActivity.this, device.toString(), 0).show();
                        break;
                    }
                }

                if (!HasBrother) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("Warning").setMessage("Please connect to USB device").show();
                } else {
                    if (!mUsbManager.hasPermission(device))
                        mUsbManager.requestPermission(device, mPermissionIntent);

                    if (mUsbManager.hasPermission(device)) {
                        BrotherDll_USB.openport(mUsbManager, device);
                        BrotherDll_USB.clearbuffer();
                        BrotherDll_USB.sendImagebyPath(this, ImgURI, 0, 0, Integer.parseInt(mEditImgW.getText().toString()), Integer.parseInt(mEditImgH.getText().toString()), 128);
                        BrotherDll_USB.printlabel(1, 1);
                        BrotherDll_USB.closeport(5000);
                    }
                }
            }
        } catch (Exception ex) {

        }

    }

    public String getNameFromURI(Uri contenturi) {

        String[] proj = {OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE};
        String name = null;
        int size = 0;
        Cursor metadataCursor = getContentResolver().query(contenturi, proj, null, null, null);

        if (metadataCursor != null) {
            try {
                if (metadataCursor.moveToFirst()) {
                    name = metadataCursor.getString(0);
                    size = metadataCursor.getInt(1);
                }
            } finally {
                metadataCursor.close();
            }
        }

        return name;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_OPEN_PDF) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                System.out.println("printFile path uri:" + uri + ", " + uri.getPath());

                PDFURI = uri;

                TotalPage = BrotherDll_wifi.getPDFPageCountbyPath(this, uri);
                if (TotalPage > 0) mTextPDF2.setText("(1~" + TotalPage + ", 0: Print All)");
                else mTextPDF2.setText("Invalid PDF File");
                String name = getNameFromURI(uri);
                mTextPDF_Name.setText(name);

            } else {
                Toast.makeText(getApplicationContext(), "Invalid File Format", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == FILE_OPEN_IMG) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                System.out.println("printFile path uri:" + uri + ", " + uri.getPath());

                ImgURI = uri;
                String name = getNameFromURI(uri);
                mTextImg_Name.setText(name);

            } else {
                Toast.makeText(getApplicationContext(), "Invalid File Format", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                System.out.println("printFile:" + id);
                try {
                    Uri contentUri = uri;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                        contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                } catch (NumberFormatException e) {
                    if (id != null && id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "");
                    }
                    return null;
                }
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = MediaStore.Images.Media.DATA;
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }
}
