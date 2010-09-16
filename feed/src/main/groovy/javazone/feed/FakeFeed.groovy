package javazone.feed

class FakeFeed {

    static def tweets = [
        new Tweet("pmmonkey", "Created a video of the demo I intend to hold in my JavaZone 2010 lightning talk: http://bit.ly/dypnka Quality is so-so, but concept clear?"),
        new Tweet("evanchooly", "finally getting this comet app figured out.  hope to have it done for contrast during my #javazone presention"),
        new Tweet("pakoito", "Se celebra una JavaZone aquí al lado y pilla justo los días que tengo que bajar a Salam de exámenes. ARG PD: Y me pierdo la fiesta Las Vegas "),
        new Tweet("matkar", "@matshenricson Then jaoo wont cut it for you any moore. No Java track and asp.net and c# talks! Devoxx, JavaZone &amp; Jfokus will mostly... "),
        new Tweet("mdeardeuff", "@darthtrevino you should see this one #javazone: http://youtu.be/1JZnj4eNHXE"),
        new Tweet("milos_matic", "LadyJava Music Video for JavaZone (High quality) http://t.co/LlotD0P via @youtube"),
        new Tweet("andrea_gioia", "LadyJava Music Video for JavaZone: http://bit.ly/9hFS4P &gt; LOL. Better than the original :D"),
        new Tweet("KnutHellan", "#couchdb talk for #javazone finished. Time to relax"),
        new Tweet("sigesaba", "Java Zone 2010のミュージックビデオ。Lady Javaてw よく出来ていると思います。http://bit.ly/9pdhe5 #javazone"),
        new Tweet("bym0m0", "Empezaremos un video muuuy geek solo un \"real geek \"lo entendera XD http://sacurativo.blogspot.com/2010/08/javazone-by-lady-java.html"),
        new Tweet("karianneberg ", "At @ninaheitmann's apart in Oslo working with our #javazone preso. Screams of \"Ninja!\" and \"WTF?\" can be heard from miles away."),
        new Tweet("Dayanx ", "Lady Java ... javazone javazone (8) .. http://ow.ly/2wi1S "),
        new Tweet("sacurativo ", "JavaZone by Lady Java http://dlvr.it/4P9Jj "),
        new Tweet("twimprine ", "JavaZone - Music video about #Java and a play on #LadyGaga http://youtu.be/1JZnj4eNHXE"),
        new Tweet("twtlrheat ", "JavaZone Trailer: Java 4-ever http://bit.ly/cZOHAO ")
    ]

    static def userLocations = [
        "pmmonkey"     : new Location(59.91228, 10.74998),
        "evanchooly"   : new Location(40.692455, -73.990364),
        "pakoito"      : new Location(25.947777, -80.315344),
        "matkar"       : new Location(59.33228, 18.06284),
        "mdeardeuff"   : new Location(47.60356, -122.329439),
        "milos_matic"  : new Location(44.811877, 20.464145),
        "andrea_gioia" : new Location(45.468945, 9.18103),
        "KnutHellan"   : new Location(63.431005, 10.39208),
        "sigesaba"     : new Location(34.576859, 135.462952),
        "bym0m0"       : new Location(12.000000, -60.000000),
        "karianneberg" : new Location(60.390705, 5.33275),
        "Dayanx"       : new Location(-12.0436, -77.021217),
        "sacurativo"   : new Location(15.59305, 120.739067),
        "twimprine"    : new Location(29.95369, -90.077714),
        "twtlrheat"    : new Location(35.159512, -98.442513)
    ]

    public static void main(String[] args) {
        def feed = new JavaZoneFeed()
        feed.tweets = this.tweets
        feed.userLocations = this.userLocations
        feed.run()
    }
}

