//ROLL:2007097

/* 
This implementation utilizes two design patterns to improve system functionality. 
The bridge design pattern connects remotes with TVs, enabling easy addition of new TV models or remote types without conflicts.
 This separation between remotes and TVs enhances scalability and flexibility. 
 Additionally, a proxy design pattern manages YouTube operations, optimizing memory usage by loading and running YouTube only when needed. 
 This proxy acts as a stand-in for the actual YouTube application, boosting resource efficiency and system performance.*/

interface TV {

    boolean isEnabled();

    void enable();

    void disable();

    int getVolume();

    void setVolume(int vol);

    int getChannel();

    void setChannel(int channel);

}

class SmartTV implements TV {
    boolean powerOn = false;
    int volume = 40;
    int channel = 1;

    @Override
    public boolean isEnabled() {
        return powerOn;
    }

    @Override
    public void enable() {
        powerOn = true;
    }

    @Override
    public void disable() {
        powerOn = false;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public void setVolume(int vol) {
        volume = vol;
    }

    @Override
    public int getChannel() {
        return channel;
    }

    @Override
    public void setChannel(int channel) {
        this.channel = channel;
    }

    public void Youtube(YoutubeClass yt) {
        System.out.println("Youtube for SmartTV::");
        yt.runYoutube();
    }
}

class GeneralTV implements TV {
    boolean powerOn = false;
    int volume = 20;
    int channel = 1;

    @Override
    public boolean isEnabled() {
        return powerOn;
    }

    @Override
    public void enable() {
        powerOn = true;
    }

    @Override
    public void disable() {
        powerOn = false;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public void setVolume(int vol) {
        volume = vol;
    }

    @Override
    public int getChannel() {
        return channel;
    }

    @Override
    public void setChannel(int channel) {
        this.channel = channel;
    }
}

class Remote {
    protected TV tv;

    Remote() {
        tv = null;
    }

    Remote(TV tv) {
        this.tv = tv;
    }

    public void togglePower() {
        if (tv.isEnabled()) {
            tv.disable();
            System.out.println("Power turned off");
        } else {
            tv.enable();
            System.out.println("Power turned on");
        }
    }

    public void volumeUp() {
        if (tv.isEnabled()) {
            tv.setVolume(tv.getVolume() + 5);
            System.out.println("Increasing volume +5");
        } else {
            System.out.println("Please Turn on tv first");
        }
    }

    public void volumeDown() {
        if (tv.isEnabled()) {
            tv.setVolume(tv.getVolume() - 5);
            System.out.println("Decreasing volume -5");
        } else {
            System.out.println("Please Turn on tv first");
        }

    }

    public void channelUp() {
        if (tv.isEnabled()) {
            tv.setChannel(tv.getChannel() + 1);
            System.out.println("Channel +1");
        } else {
            System.out.println("Please Turn on tv first");
        }
    }

    public void channelDown() {
        if (tv.isEnabled()) {
            tv.setChannel(tv.getChannel() - 1);
            System.out.println("Channel -1");
        } else {
            System.out.println("Please Turn on tv first");
        }
    }
}

class SmartRemote extends Remote {

    SmartRemote() {

    }

    SmartRemote(TV tv) {
        super(tv);
    }

    void showYoutube(YoutubeClass yt) {
        ((SmartTV) tv).Youtube(yt);
    }
}

// YoutubeClass is used to implement Proxy Design method
// For the first-time it will load youtube but further it will run itself without loading.

interface YoutubeClass {
    void runYoutube();
}

class FirstLoadYoutube implements YoutubeClass {

    @Override
    public void runYoutube() {
        System.out.println("->Running Youtube");
    }
}

class ProxyLoadYoutube implements YoutubeClass {
    private FirstLoadYoutube firstLoadYoutube;

    @Override
    public void runYoutube() {
        if (firstLoadYoutube == null) {
            firstLoadYoutube = new FirstLoadYoutube();
            System.out.println("->Starting Youtube");
        }
        firstLoadYoutube.runYoutube();
    }
}

public class Main {
    public static void main(String[] args) {

        // GeneralTV


        GeneralTV gtv = new GeneralTV();
        Remote remote = new Remote(gtv);
        System.out.println("General Tv::");
        remote.togglePower();
        remote.volumeUp();
        remote.channelUp();
        remote.channelDown();
        remote.volumeDown();


        // SmartTV

        System.out.println();
        System.out.println("Smart Tv::");
        SmartTV stv = new SmartTV();
        SmartRemote sremote = new SmartRemote(stv);
        sremote.togglePower();
        sremote.volumeUp();
        sremote.channelUp();
        sremote.channelDown();
        sremote.volumeDown();

        System.out.println();
        // Youtube Class
        YoutubeClass yt = new ProxyLoadYoutube();

        // Youtube() using smart-remote.
        sremote.showYoutube(yt);
        sremote.showYoutube(yt);
        // Direct accessing Youtube().
        
    }
}
