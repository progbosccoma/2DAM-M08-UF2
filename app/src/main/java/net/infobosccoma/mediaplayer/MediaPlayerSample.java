package net.infobosccoma.mediaplayer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.IOException;

/**
 * Exemple que mostra com reproduir un àudio des d'un fitxer emmagatzemat a Internet
 *
 * @author Marc Nicolau
 *
 */
public class MediaPlayerSample extends Activity implements MediaPlayer.OnPreparedListener {

	static final String AUDIO_PATH = "http://www.susanpiver.com/audio/Dancing%20in%20the%20Dark.mp3";
	// l'objecte amb el qual es fa la reproducció del fitxer
	private MediaPlayer mediaPlayer;
    // progressbar
    private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_player);

        pd = new ProgressDialog(this);
        // mostrar progress bar
        pd.setMessage(getString(R.string.load_message));

        mediaPlayer = new MediaPlayer();
        // indicar tipus d'àudio a reproduir
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // indicar àudio a reproduir
        try {
            mediaPlayer.setDataSource(AUDIO_PATH);
            // listener per quan ja està disponible per ser reproduït
            mediaPlayer.setOnPreparedListener(this);
        } catch (IOException e) {
            Dialeg.newInstance("Error", e.getMessage())
                    .show(getFragmentManager(),"errorDialog");
        }
    }

	/**
	 * Implementació de l'esdeveniment clic del botó
	 * @param v
	 */
	public void btnClick(View v) {
		Button b = (Button) v;
		if (b.getText().equals(getText(R.string.reproduir))) {
            // preparar l'àudio asíncronament
            mediaPlayer.prepareAsync();
            // mostrar progressbar
            pd.show();
            // canviar el text del botó a aturar
            b.setText(getText(R.string.aturar));

        } else {
			b.setText(getText(R.string.reproduir));
			mediaPlayer.stop();
		}
	}

	
    @Override
    public void onPrepared(MediaPlayer mp) {
        // iniciar la reproducció
        mp.start();
        // amagar el progressbar
        pd.dismiss();
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mediaPlayer != null) {
			mediaPlayer.release();
		}
	}


}
