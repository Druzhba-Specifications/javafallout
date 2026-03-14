package com.newrussia.game;

import java.util.List;
import java.util.Map;

public final class DemoContent {
    private DemoContent() {
    }

    public static GameState createInitialState() {
        Player player = new Player(
                "Courier Vostok",
                new SpecialStats(6, 7, 6, 5, 7, 8, 4),
                List.of(
                        new Perk("Sniper's Breath", "+15% crit chance on aimed shots."),
                        new Perk("Metro Ghost", "Reduced AP cost while sneaking in underground maps."),
                        new Perk("Rad Linguist", "+20 Speech when speaking with faction leaders.")
                )
        );

        Npc babushka = new Npc(
                "Engineer Babushka Irina",
                "Mechanist of the Novaya Metro",
                "irina",
                List.of(
                        new DialogueNode("Irina", "You survived the ash storm. Good. The Republic still needs runners."),
                        new DialogueNode("Irina", "General Makarov sealed the Sky Elevator. Find proof of his deal."),
                        new DialogueNode("Irina", "Listen for the old anthem in the radio static. That's where vault doors hide.")
                ),
                42,
                "female_gravel"
        );

        Npc broker = new Npc(
                "Sable Broker Kirov",
                "Black-market information dealer",
                "kirov",
                List.of(
                        new DialogueNode("Kirov", "Secrets are expensive. But I like your eyes; they look desperate."),
                        new DialogueNode("Kirov", "There is a hidden bunker under the frozen ballet theater."),
                        new DialogueNode("Kirov", "Bring me a reactor core and I'll open every checkpoint in this city.")
                ),
                58,
                "male_smooth"
        );

        Location metro = new Location(
                "novaya_metro",
                "Novaya Metro",
                "A cathedral of steel rails lit by cracked neon saints. Refugees trade bullets for soup while propaganda echoes above.",
                "Cold blue tile walls, flickering sodium lamps, rusted rail carts",
                List.of("red_square_ruins", "volga_shore"),
                List.of(babushka),
                List.of(new Enemy("Tunnel Ghoul", 35, 7, 2, 30)),
                List.of("Signal Chapel: an off-map prayer room with pre-war maps", "Vent 13: hidden med stash"),
                "A monorail screams through the dark as your pipboy marks New Russia's frontier."
        );

        Location redSquare = new Location(
                "red_square_ruins",
                "Red Square Ruins",
                "The iconic square is buried in ash. Tank husks form walls and giant banners flap over sniper nests.",
                "Burnt red stone, scorched bronze statues, drifting ash particles",
                List.of("novaya_metro", "kremlin_depths"),
                List.of(broker),
                List.of(new Enemy("Makarov Guard", 55, 11, 5, 55), new Enemy("War Hound", 42, 10, 3, 45)),
                List.of("Lenin Vault hatch behind shattered mausoleum", "Clocktower stash with prototype rifle"),
                "You step over cracked mosaics while an old loudspeaker declares victory to nobody."
        );

        Location kremlin = new Location(
                "kremlin_depths",
                "Kremlin Depths",
                "A subterranean command labyrinth with biometric locks, mutant spores, and holotapes from the Final Winter.",
                "Military concrete, holographic maps, green emergency strobes",
                List.of("red_square_ruins", "volga_shore"),
                List.of(),
                List.of(new Enemy("Spore Brute", 75, 14, 6, 85), new Enemy("Autoturret MK-IV", 40, 12, 8, 60)),
                List.of("Vault Directorate chamber behind icon wall", "Cryo archive with companion backstories"),
                "Ancient blast doors open as choirs and Geiger clicks merge into one terrible rhythm."
        );

        Location volga = new Location(
                "volga_shore",
                "Volga Shore Fortress",
                "A trade fortress on frozen riverbanks where smugglers dock jury-rigged hovercrafts.",
                "Icy steel piers, amber floodlights, improvised market tents",
                List.of("novaya_metro", "kremlin_depths"),
                List.of(),
                List.of(new Enemy("River Raider", 48, 9, 4, 40)),
                List.of("Submerged customs office with gold reserve", "Ghost lighthouse elevator to DLC zone"),
                "At dusk, the Volga glows like broken glass while smugglers sing old cosmonaut songs."
        );

        return new GameState(player,
                Map.of(
                        metro.id(), metro,
                        redSquare.id(), redSquare,
                        kremlin.id(), kremlin,
                        volga.id(), volga
                ),
                metro.id());
    }
}
