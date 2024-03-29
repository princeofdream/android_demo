package com.rkdemo.gpiodemo;
import android.util.Log;

public class HardwareControler
{
    /* I/O */
	/*
    static public native int open(String devName, int flags);
    static public native int write(int fd, byte[] data);
    static public native int read(int fd, byte[] buf, int len);
    static public native int select(int fd, int sec, int usec);
    static public native void close(int fd);
    static public native int ioctlWithIntValue(int fd, int cmd, int value);
    static public native int ioctl(int fd, int cmd, byte[] buf);
	*/

    /* Serial Port */
	/*
    static public native int openSerialPort( String devName, long baud, int dataBits, int stopBits );
    static public native int openSerialPortEx( String devName
            , long baud
            , int dataBits
            , int stopBits
            , String parityBit
            , String flowCtrl
            );
	*/

    /* LED */
	/*
    static public native int setLedState( int ledID, int ledState );
	*/

    /* PWM */
	/*
    static public native int PWMPlay(int frequency);
    static public native int PWMStop();
    static public native int PWMPlayEx(int gpioPin, int frequency);
    static public native int PWMStopEx(int gpioPin);
	*/

    /* ADC */
	/*
    static public native int readADC();
    static public native int readADCWithChannel(int channel);
    static public native int[] readADCWithChannels(int[] channels);
	*/

    /* I2C */
	/*
    static public native int setI2CSlave(int fd, int slave);
    static public native int setI2CTimeout(int fd, int timeout);
    static public native int setI2CRetries(int fd, int retries);
    static public native int I2CReadByteData(int fd, int pos, int wait_us);
    static public native int I2CReadByte(int fd, int wait_us);
    static public native int I2CWriteByteData(int fd, int pos, byte byteData, int wait_us);
    static public native int I2CWriteByte(int fd, byte byteData, int wait_us);
	*/

    /* Discard */ static public native int readByteFromI2C(int fd, int pos, int wait_ms);
    /* Discard */ static public native int writeByteToI2C(int fd, int pos, byte byteData, int wait_ms);
    /* Discard */ static public native int writeByteToI2C2(int fd, byte byteData, int wait_ms);

    /* SPI */
	/*
    static public native int setSPIWriteBitsPerWord( int spi_fd, int bits );
    static public native int setSPIReadBitsPerWord( int spi_fd, int bits );
    static public native int setSPIBitOrder( int spi_fd, int order);
    static public native int setSPIClockDivider( int spi_fd, int divider);
    static public native int setSPIMaxSpeed( int spi_fd, int spi_speed);
    static public native int setSPIDataMode( int spi_fd, int mode);
    static public native int SPItransferOneByte( int spi_fd, byte byteData, int spi_delay, int spi_speed, int spi_bits);
    static public native int SPItransferBytes(int spi_fd, byte[] writeData, byte[] readBuff, int spi_delay, int spi_speed, int spi_bits);
    static public native int writeBytesToSPI(int spi_fd, byte[] writeData, int spi_delay, int spi_speed, int spi_bits);
    static public native int readBytesFromSPI(int spi_fd, byte[] readBuff, int spi_delay, int spi_speed, int spi_bits);
	*/

    /* GPIO */
    static public native int exportGPIOPin(int pin);
    static public native int unexportGPIOPin(int pin);
    //GPIOEnum.LOW or GPIOEnum.HIGH
    static public native int setGPIOValue(int pin, int value);
    static public native int getGPIOValue(int pin);
    //GPIOEnum.IN or GPIOEnum.OUT
    static public native int setGPIODirection(int pin, int direction);
    static public native int getGPIODirection(int pin);

	static public native int checkpath(String str_path);

	static public native String getStringFormC();
    static public native String stringFromJNI();


    static {
        try {
            System.loadLibrary("gpiodemo");
        } catch (UnsatisfiedLinkError e) {
            Log.d("HardwareControler", "lib gpiodemo library not found!");
        }
    }

    static public int native_exportGPIOPin(int pin)
	{
		return exportGPIOPin(pin);
	}

    static public int native_unexportGPIOPin(int pin)
	{
		return unexportGPIOPin(pin);
	}

    static public int native_setGPIODirection(int pin, int val)
	{
		return setGPIODirection(pin, val);
	}

    static public int native_getGPIODirection(int pin)
	{
		return getGPIODirection(pin);
	}

    static public int native_setGPIOValue(int pin, int val)
	{
		return setGPIOValue(pin,val);
	}

    static public int native_getGPIOValue(int pin)
	{
		return getGPIOValue(pin);
	}

    static public int native_checkpath(String str_path)
	{
		return checkpath(str_path);
	}

}
