package Restaurante_app.Componentes;

import java.util.ArrayList;
import java.util.List;

import Restaurante_app.Models.Producto;

public class MenuDelDiaSGT {
	private static MenuDelDiaSGT instance;
    private List<Producto> menu;

    private MenuDelDiaSGT() {
        menu = new ArrayList<>();
    }

    public static MenuDelDiaSGT getInstance() {
        if (instance == null) {
            instance = new MenuDelDiaSGT();
        }
        return instance;
    }

    public void setMenu(List<Producto> menu) {
        this.menu = menu;
    }

    public List<Producto> getMenu() {
        return menu;
    }
}
