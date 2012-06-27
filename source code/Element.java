package Application;


public class Element
{
	public String term;
	public double zahl;
	public char sign;
	public char variable;

	private boolean isTerm;
	private boolean isZahl;
	private boolean isSign;
	private boolean isVar;
	private boolean isCalcable;
	
	public Element(String newTerm)
	{
		term = newTerm;
		
		isCalcable = true;
		
		for(int i=0; i<newTerm.length(); i++)
		{
			if(newTerm.charAt(i) == 'x')
			{
				isCalcable = false;
			}
		}
		
		isTerm = true;
		isZahl = false;
		isSign = false;
	}
	
	public Element(double newZahl)
	{
		zahl = newZahl;
		
		isTerm = false;
		isZahl = true;
		isSign = false;
	}
	
	public Element(char c, boolean isVariable)
	{
		if(isVariable == true)
		{
			System.out.println(c);
			variable = c;
			isVar = true;
			isSign = false;
			isCalcable = false;
		}
		else
		{
			sign = c;
			isVar = false;
			isSign = true;
		}
		
		isTerm = false;
		isZahl = false;
	}
	
	public void setCalcable(boolean newCalcable)
	{
		isCalcable = newCalcable;
	}
	
	public boolean isTerm()
	{
		return isTerm;
	}
	
	public boolean isZahl()
	{
		return isZahl;
	}
	
	public boolean isSign()
	{
		return isSign;
	}
	
	public boolean isVar()
	{
		return isVar;
	}
	
	public boolean isCalcable()
	{
		return isCalcable;
	}
	
	public String getString()
	{
		if(isTerm)
			return term;
		else if(isZahl)
			return String.valueOf(zahl);
		else if(isSign)
			return String.valueOf(sign);
		else if(isVar)
			return String.valueOf(variable);
		
		return null;
	}
}
