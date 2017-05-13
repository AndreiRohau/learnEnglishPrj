package api;

import module.Module;

import static api.Util.outWrite;

/**
 * Created by rohau.andrei on 14.05.2017.
 */
public class MainMenu {

    //метода по созданию объекта из конструктора модулЬ()!!!! ОБЪЕКТ СОЗДАН!!! ЗАПОЛНИМ ЕГО ФРАЗАМИ И ДАДИМ МЕНЮ!
    // , и заполнение его методом ДАО ОСТАВШЕЙСЯ инфой из БД

    //менюшка с возможностями проги и управление обучением, при помощи готового закгруженого пройфайла в модуль

    public static void letsStudy(Module user){
        outWrite("Hello MR: " + user.toString());
    }
}
