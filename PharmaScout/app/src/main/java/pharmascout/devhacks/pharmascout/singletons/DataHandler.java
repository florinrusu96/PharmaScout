package pharmascout.devhacks.pharmascout.singletons;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pharmascout.devhacks.pharmascout.model.FarmacieModel;

/**
 * Created by robert.damian on 11/20/2016.
 */

public class DataHandler {

    private static DataHandler dataHandler;

    protected DataHandler() {

    }

    public static DataHandler getInstance() {
        if (dataHandler == null) {
            dataHandler = new DataHandler();
        }

        return dataHandler;
    }

    public List<FarmacieModel> filterOutFarmaciiInchise(List<FarmacieModel> farmaciiGasite) {
        Calendar now = Calendar.getInstance();
        Calendar oraInchidere = Calendar.getInstance();
        Calendar oraDeschidere = Calendar.getInstance();

        List<FarmacieModel> result = new ArrayList<>();

        for (FarmacieModel farmacie : farmaciiGasite) {
            oraInchidere.set(Calendar.HOUR_OF_DAY, getOraInchidere(farmacie.getProgram()));
            oraInchidere.set(Calendar.MINUTE, getMinutInchidere(farmacie.getProgram()));

            oraDeschidere.set(Calendar.HOUR_OF_DAY, getOraDeschidere(farmacie.getProgram()));
            oraDeschidere.set(Calendar.MINUTE, getMinutDeschidere(farmacie.getProgram()));

            if (now.after(oraDeschidere) && now.before(oraInchidere)) {
                result.add(farmacie);
            }

        }

        return result;
    }

    private int getOraDeschidere(String program) {
        return Integer.valueOf(program.split(":")[0]);
    }

    private int getMinutDeschidere(String program) {
        return Integer.valueOf(program.split("-")[0].split(":")[1]);
    }

    private int getOraInchidere(String program) {
        return Integer.valueOf(program.split("-")[1].split(":")[0]);
    }

    private int getMinutInchidere(String program) {
        return Integer.valueOf(program.split(":")[2]);
    }

}
