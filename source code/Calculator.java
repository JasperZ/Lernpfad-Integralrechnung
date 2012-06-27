package Application;
import java.util.ArrayList;

public class Calculator
{
	public static double allesRechnen(String input)
	{
		double ergebins = 0;
		ArrayList<Element> rechnung;
		
		rechnung = parseInput(input);
		
		for(int i=0; i<rechnung.size(); i++)
		{
			if(rechnung.get(i).isTerm())
			{
				rechnung.set(i, new Element(allesRechnen(rechnung.get(i).term)));
			}
		}
		
		grundoperatoren(rechnung);
		
		ergebins = rechnung.get(0).zahl;
		
		return ergebins;
	}
	
	public static String aufleiten(String input)
	{
		String ableitung = "";
		ArrayList<Element> rechnung;
		int zahlen = 0;
		
		rechnung = parseInputFunction(input);
		
		for(int i=0; i<rechnung.size(); i++)
		{
			if(rechnung.get(i).isCalcable())
			{
				rechnung.set(i, new Element(allesRechnen(rechnung.get(i).getString())));
			}
		}
		
		for(int i=0; i<rechnung.size(); i++)
		{
			if(rechnung.get(i).isZahl())
			{
				zahlen++;
			}
		}
		
		if(zahlen > 1)
		{
			String a, b, s;
			boolean aa, bb;
			
			a = "";
			b = "";
			s = "";
			aa = false;
			bb = false;
			
			for(int i=0; i<rechnung.size(); i++)
			{
				if(rechnung.get(i).isZahl())
				{
					if(aa == false)
					{
						a = rechnung.get(i).getString();
						rechnung.set(i, new Element(0.0));
						aa = true;
					}
					else
					{
						s = rechnung.get(i-1).getString();
						b = rechnung.get(i).getString();
						bb = true;
					}
				}
				
				if(aa && bb)
				{
					rechnung.set(i, new Element(Calculator.allesRechnen("("+a+")"+s+"("+b+")")));
					aa = false;
					bb = false;
				}
			}
		}

		for(int i=0; i<rechnung.size(); i++)
		{
			if(rechnung.get(i).isTerm())
			{
				String term = rechnung.get(i).term;
				char c;
				double e;
				boolean exp = false;
				
				for(int j=0; j<term.length(); j++)
				{
					c = term.charAt(j);
					
					if(c == '^')
					{
						exp = true;
						break;
					}
				}
				
				if(exp == false)
				{
					term = term.replaceAll("x", "x^1");
				}
				
				for(int j=0; j<term.length(); j++)
				{
					c = term.charAt(j);
					
					if(c == 'x')
					{
						if(term.charAt(j+1) == '^')
						{
							e = Double.valueOf(term.substring(j+2, term.length()-1));
							
							term = term.substring(0, term.length()-1)+"*x/"+(e+1.0)+")";
						}
					}
				}
				
				rechnung.set(i, new Element(term));
			}
		}
		
		for(int i=0; i<rechnung.size(); i++)
		{
			if(rechnung.get(i).isZahl())
			{
				rechnung.set(i, new Element("("+rechnung.get(i).getString()+"*x"+")"));
			}
		}
		
		for(int i=0; i<rechnung.size(); i++)
		{
			ableitung += rechnung.get(i).getString();
		}
		
		return ableitung;
	}
	
	public static ArrayList<Element> parseInputFunction(String input)
	{
		ArrayList<Element> rechnung;
		String buffer;
		char c;
		boolean firstChar;

		rechnung = new ArrayList<Element>();
		buffer = "";
		firstChar = true;
		
		input = input.replace(',', '.');
		input = input.replace(':', '/');
		
		for(int i=0; i<input.length(); i++)
		{
			c = input.charAt(i);
			
			if(!firstChar && (c == '+' || c == '-'))
			{
				rechnung.add(new Element("("+buffer+")"));
				rechnung.add(new Element(c, false));
				
				buffer = "";
			}
			else
			{
				buffer += c;
			}
			
			firstChar = false;
		}
		
		if(!buffer.equals(""))
		{
			rechnung.add(new Element("("+buffer+")"));
		}
		
		return rechnung;
	}
	
	public static ArrayList<Element> parseInput(String input)
	{
		ArrayList<Element> rechnung;
		String buffer;
		char c;
		int klammern;
		boolean first;

		rechnung = new ArrayList<Element>();
		buffer = "";
		klammern = 0;
		first = true;
		
		input = input.replaceAll("t", "");
		input = input.replace(',', '.');
		input = input.replace(':', '/');
		
		for(int i=0; i<input.length(); i++)
		{
			c = input.charAt(i);
			
			if((c >= '0' && c <= '9') || c == '.')
			{
				buffer += c;
			}
			else if(c == '+' || c == '-' || c == '*' || c == '/' || c == '^')
			{
				if(first)
				{
					buffer += c;
				}
				else
				{
					if(klammern == 0)
					{
						if(!buffer.equals(""))
						{
							rechnung.add(new Element(Double.parseDouble(buffer)));
						}
						
	    				rechnung.add(new Element(c, false));
	    				
	    				buffer = "";
					}
					else
					{
						buffer += c;
					}
				}
			}
			else if(c == '(')
			{
				if(klammern == 0)
				{
					c = 't';
					buffer += c;
				}
				else
				{
					buffer += c;
				}
				
				klammern++;
			}
			else if(c == ')')
			{
				klammern--;
				
				if(klammern == 0)
				{
					rechnung.add(new Element(buffer));
					
					buffer = "";
				}
				else
				{
					buffer += c;
				}
			}

			first = false;
		}
		
		if(!buffer.equals(""))
		{
			rechnung.add(new Element(Double.parseDouble(buffer)));
		}
		
		return rechnung;
	}
	
	public static void grundoperatoren(ArrayList<Element> rechnung)
	{
		for(int i=0; i<rechnung.size(); i++)
		{
			Element e = rechnung.get(i);
			
			if(e.isSign())
			{
				if(e.getString().equals("^"))
				{
					double a,b, erg;
					
					a = rechnung.get(i-1).zahl;
					b = rechnung.get(i+1).zahl;
					
					erg = Math.pow(a, b);
					
					i--;
					
					rechnung.remove(i);
					rechnung.remove(i);
					
					rechnung.set(i, new Element(erg));
				}
			}
		}
		
		for(int i=0; i<rechnung.size(); i++)
		{
			Element e = rechnung.get(i);
			
			if(e.isSign())
			{
				if(e.getString().equals("*"))
				{
					double a,b, erg;
					
					a = rechnung.get(i-1).zahl;
					b = rechnung.get(i+1).zahl;
					
					erg = a*b;
					
					i--;
					
					rechnung.remove(i);
					rechnung.remove(i);
					
					rechnung.set(i, new Element(erg));
				}
				else if(e.getString().equals("/"))
				{
					double a,b, erg;
					
					a = rechnung.get(i-1).zahl;
					b = rechnung.get(i+1).zahl;
					
					erg = a/b;
					
					i--;
					
					rechnung.remove(i);
					rechnung.remove(i);
					
					rechnung.set(i, new Element(erg));
				}
			}
		}
		
		for(int i=0; i<rechnung.size(); i++)
		{
			Element e = rechnung.get(i);
			
			if(e.isSign())
			{
				if(e.getString().equals("+"))
				{
					double a,b, erg;
					
					a = rechnung.get(i-1).zahl;
					b = rechnung.get(i+1).zahl;
					
					erg = a+b;
					
					i--;
					
					rechnung.remove(i);
					rechnung.remove(i);
					
					rechnung.set(i, new Element(erg));
				}
				else if(e.getString().equals("-"))
				{
					double a,b, erg;
					
					a = rechnung.get(i-1).zahl;
					b = rechnung.get(i+1).zahl;
					
					erg = a-b;
					
					i--;
					
					rechnung.remove(i);
					rechnung.remove(i);
					
					rechnung.set(i, new Element(erg));
				}
			}
		}
	}

	public static double calcArea(String functionString1, String functionString2)
	{
		double result, tmp1, tmp2;
		String aufleitung1, aufleitung2;
		
		aufleitung1 = aufleiten(functionString1);
		aufleitung2 = aufleiten(functionString2);
		
		tmp1 = Math.abs(allesRechnen(aufleitung1.replaceAll("x", "(-2)")) - allesRechnen(aufleitung2.replaceAll("x", "(-2)")));
		tmp2 = Math.abs(allesRechnen(aufleitung1.replaceAll("x", "(2)")) - allesRechnen(aufleitung2.replaceAll("x", "(2)")));
		
		result = tmp1 - tmp2;
		
		System.out.println("tmp1: "+tmp1);
		System.out.println("tmp2: "+tmp2);
		
		return result;
	}
}
