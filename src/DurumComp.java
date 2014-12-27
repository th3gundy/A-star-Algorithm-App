import java.util.Comparator;

public class DurumComp implements Comparator{
	//kucukten buyuge oncelikli kuyrugu yoneten sinif
	@Override
	public int compare(Object v, Object y) {
		if(((SimdiDurum)v).f>((SimdiDurum)y).f)
			return 1;
		else
			return -1;
	}
}
