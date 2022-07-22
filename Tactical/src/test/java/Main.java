import net.crossager.tactical.UnitTacticalAPI;
import net.crossager.tactical.api.config.TacticalConfigSerializer;
import net.crossager.tactical.api.config.TacticalFileConfig;
import net.crossager.tactical.api.config.items.TacticalConfigSection;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        UnitTacticalAPI.init();

        TacticalFileConfig testData = TacticalFileConfig.create(TacticalConfigSerializer.json(), "data");

        testData.options().prettyPrint(true).tabSize(2);
        testData.createList("test.hest");
        TacticalConfigSection section = testData.get("test.hest").asList().addList().addSection();
        section.set("2", "4");
        section.set("3", "5");
        testData.save();
    }
}
