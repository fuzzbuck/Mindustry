package mindustry.ui;

import arc.Core;
import arc.graphics.Color;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Stack;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import mindustry.gen.Icon;
import mindustry.world.meta.StatUnit;

/** An ItemDisplay, but for heat. */
public class HeatDisplay extends Table{
    public final float amount;
    public final boolean perSecond;

    public HeatDisplay(float amount, boolean perSecond){
        this.amount = amount;
        this.perSecond = perSecond;

        add(new Stack(){{
            add(new Image(Icon.effect.getRegion()));

            if(amount != 0){
                Table t = new Table().left().bottom();
                t.add(Strings.autoFixed(amount, 1)).style(Styles.outlineLabel);
                add(t);
            }
        }}).size(8 * 4).padRight(3  + (amount != 0 && Strings.autoFixed(amount, 1).length() > 2 ? 8 : 0));

        add(Core.bundle.get("content.heat.name"));

        if(perSecond){
            add(StatUnit.perSecond.localized()).padLeft(2).padRight(5).color(Color.lightGray).style(Styles.outlineLabel);
        }

    }
}
