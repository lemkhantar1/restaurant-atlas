package panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import composantElementaire.MonPasswordField;
import composantElementaire.MonTextField;
import ecouteur.Ecouteur;
import fenetres.FenetrePrincipale;

public class AuthentificationPan extends JPanel
{
	/*the default image to use*/
	FenetrePrincipale fenetre;
	String imageFile = "restaurant.png";
	JPanel authenPan = new JPanel();
		JLabel erreurLabel = new JLabel("Bonjour ! identifiez-vous...");
		MonTextField login = new MonTextField(20);
		MonPasswordField mdp = new MonPasswordField(20);
		JButton buttonlogin;
	
	
	public AuthentificationPan(FenetrePrincipale fenetre)
	{
		super();
		this.fenetre = fenetre;
		this.setLayout(null);
		
		authenPan.setLayout(new GridLayout(4, 1));
		
		authenPan.add(erreurLabel);
		erreurLabel.setForeground(Color.black);
		erreurLabel.setBackground(new Color(255, 255, 255, 100));
		erreurLabel.setOpaque(true);
		
		Font font = new Font(" TimesRoman ",Font.BOLD,15);
		erreurLabel.setFont(font);
		erreurLabel.setHorizontalAlignment(0);
		
		authenPan.add(login);
		login.setPlaceholder("Login");
		login.setHorizontalAlignment(0);
		
		authenPan.add(mdp);
		mdp.setHorizontalAlignment(0);
		mdp.setPlaceholder("******");
		mdp.setHorizontalAlignment(0);
		
		authenPan.setBounds(200, 250, 300, 200);
		authenPan.setOpaque(false);
		
		BufferedImage buttonIcon;
		try {
			buttonIcon = ImageIO.read(new File("arrow.png"));
			buttonlogin = new JButton(new ImageIcon(buttonIcon));
			buttonlogin.setBorder(BorderFactory.createEmptyBorder());
			buttonlogin.addActionListener(new Ecouteur(this));
			authenPan.add(buttonlogin);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		this.add(authenPan);
	}
	
	public AuthentificationPan(String image)
	{
		super();
		this.imageFile = image;
	}
	
	public AuthentificationPan(LayoutManager layout)
	{
		super(layout);
	}
	
	public void paintComponent(Graphics g)
	{
		/*create image icon to get image*/
		ImageIcon imageicon = new ImageIcon(imageFile);
		Image image = imageicon.getImage();
	
		/*Draw image on the panel*/
		super.paintComponent(g);
	
		if (image != null)
			g.drawImage(image, 0, 0, 700, 800, this);
	}

	public JLabel getErreurLabel() {
		return erreurLabel;
	}

	public void setErreurLabel(JLabel erreurLabel) {
		this.erreurLabel = erreurLabel;
	}

	public MonTextField getLogin() {
		return login;
	}

	public void setLogin(MonTextField login) {
		this.login = login;
	}

	public MonPasswordField getMdp() {
		return mdp;
	}

	public void setMdp(MonPasswordField mdp) {
		this.mdp = mdp;
	}

	public JButton getButtonlogin() {
		return buttonlogin;
	}

	public void setButtonlogin(JButton buttonlogin) {
		this.buttonlogin = buttonlogin;
	}

	public FenetrePrincipale getFenetre() {
		return fenetre;
	}

	public void setFenetre(FenetrePrincipale fenetre) {
		this.fenetre = fenetre;
	}
}