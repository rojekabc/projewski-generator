/**
 * 
 */
package pk.ie.proj.generproj;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pk.ie.proj.exceptions.ParameterException;
import pk.ie.proj.exceptions.ViewDataException;
import pk.ie.proj.generproj.layout.AlaNullLayout;
import pk.ie.proj.interfaces.ParameterInterface;
import pk.ie.proj.interfaces.ViewDataInterface;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.Mysys;

/**
 * @author projewski
 *
 */
public class ParameterDialog extends JDialog implements ActionListener {

	private JButton dialogButtOk;
	private ParameterInterface inPi;
	private ParameterParamPack paramPack;
	private ViewDataInterface view;
	ParameterDialog(Frame  frame, ParameterInterface pi, ViewDataInterface vi)
	{
		super(frame, true);
		dialogButtOk = new JButton("Ok");
		dialogButtOk.setBounds(400, 10, 80, 25);
		dialogButtOk.addActionListener(this);
		//JPanel panel = new ParameterParamPack(pi);
		// JPanel panel = new JPanel();
		paramPack = new ParameterParamPack(pi);
		setBounds(10, 10, 500, 300);
		setContentPane(new JPanel());
		getContentPane().setLayout(new AlaNullLayout());
		paramPack.setLayout(new AlaNullLayout());
		//panel.setLayout(new AlaNullLayout());

		//paramPack = new ParameterParamPack(panel, pi);
		
		JScrollPane scrPanParam = new javax.swing.JScrollPane(paramPack);
		//scrPanParam.getViewport().setView(panel);
		scrPanParam.setBounds(10, 10, 380, 250);
		
		getContentPane().add(scrPanParam);
		getContentPane().add(dialogButtOk);
		inPi = pi;
		view = vi;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void actionPerformed(ActionEvent evt)
	{
		Object o = evt.getSource();

		if ( o == this.dialogButtOk )
		{
			paramPack.getParameterInterface(inPi);
//			Object[] paramValue;
			String[] paramName;
			try {
//				paramValue = inPi.listValues();
				paramName = inPi.listParameters();
			} catch (ParameterException e) {
				Mysys.error(e);
				return;
			}

			for (int i=0; i<paramName.length; i++)
			{
				try
				{
				if (inPi.getParameter(paramName[i]) == null)
				{
					JOptionPane.showMessageDialog( this,
							Convert.stringWrap( "Niepoprawnie ustawiona wartosc parametru "+paramName[i], 30 ),
							"Warning",
							JOptionPane.WARNING_MESSAGE );
					return;
				}
				}
				catch (ParameterException e)
				{
					Mysys.error(e);
					return;
				}
			}
			
			try {
				view.refreshView();
			} catch (ViewDataException e) {
				Mysys.error(e);
			}
			
			setVisible(false);
			return;		
		}
		
	}
	
	static void doParameterDialog(Frame  frame, ParameterInterface pi, ViewDataInterface vi)
	{
		ParameterDialog pd = new ParameterDialog(frame, pi, vi);
		pd.setVisible(true);		
	}

}
