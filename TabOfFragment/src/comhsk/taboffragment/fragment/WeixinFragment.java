package comhsk.taboffragment.fragment;

import comhsk.taboffragment.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WeixinFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.tab01, container, false);
	}
}
