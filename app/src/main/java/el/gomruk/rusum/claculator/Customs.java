package el.gomruk.rusum.claculator;

/**
 * Created by e.yusifli on 10.11.2015.
 */
public class Customs {

    private String _Position;
    private double _AsksizVergi = 0, _MuherrikinIsciHecmi, _EmeliyyatHaqqi, _VesiqeHaqqi, _EDV, _Total;

    public Customs(String Position, double asksizVergi, double MuherrikinIsciHecmi, double EmeliyyatHaqqi, double VesiqeHaqqi, double EDV, double Total) {
        _Position = Position;
        _AsksizVergi = asksizVergi;
        _MuherrikinIsciHecmi = MuherrikinIsciHecmi;
        _EmeliyyatHaqqi = EmeliyyatHaqqi;
        _VesiqeHaqqi = VesiqeHaqqi;
        _EDV = EDV;
        _Total = Total;
    }

    public String getPosition() {
        return _Position;
    }

    public double getAsksizVergi() {
        return _AsksizVergi;
    }

    public double getMuherrikinIsciHecmi() {
        return _MuherrikinIsciHecmi;
    }

    public double getEmeliyyatHaqqi() {
        return _EmeliyyatHaqqi;
    }

    public double getVesiqeHaqqi() {
        return _VesiqeHaqqi;
    }

    public double getEDV() {
        return _EDV;
    }

    public double getTotal() {
        return _Total;
    }


}
