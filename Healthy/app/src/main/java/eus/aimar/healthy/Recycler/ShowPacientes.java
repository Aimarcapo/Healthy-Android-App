package eus.aimar.healthy.Recycler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import eus.aimar.healthy.EditPacientes;
import eus.aimar.healthy.Model.Medico;
import eus.aimar.healthy.Model.Paciente;
import eus.aimar.healthy.R;
import eus.aimar.healthy.ViewModel.MedicoViewModel;
import eus.aimar.healthy.ViewModel.PacienteViewModel;
import eus.aimar.healthy.databinding.ShowPacientesBinding;
import eus.aimar.healthy.databinding.ViewholderBinding;

public class ShowPacientes extends Fragment {

    private ShowPacientesBinding binding;
    private PacienteViewModel pacienteViewModel;
    private MedicoViewModel medicoViewModel;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = ShowPacientesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        medicoViewModel = new ViewModelProvider(requireActivity()).get(MedicoViewModel.class);
        pacienteViewModel = new ViewModelProvider(requireActivity()).get(PacienteViewModel.class);
        navController = Navigation.findNavController(view);
        PacienteAdapter pacienteAdapter = new PacienteAdapter();
        Medico loggedInMedico = medicoViewModel.getLoggedInMedico();

        binding.fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(R.id.action_show_to_create);
            }
        });
        binding.fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(R.id.action_show_to_login);
            }
        });

        binding.recyclerViewPacientes.setAdapter(pacienteAdapter);
        binding.recyclerViewPacientes.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        pacienteViewModel.getPacientee(loggedInMedico.getId()).observe(getViewLifecycleOwner(), new Observer<List<Paciente>>() {
            @Override
            public void onChanged(List<Paciente> pacientes) {
                pacienteAdapter.establishList(pacientes);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final ViewholderBinding binding;

        public ViewHolder(ViewholderBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class PacienteAdapter extends RecyclerView.Adapter<ViewHolder>{
        List<Paciente> pacientes;
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ViewholderBinding.inflate(getLayoutInflater(), parent, false));
        }

        public Paciente getPaciente(int position){ return pacientes.get(position); }


        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Paciente paciente = pacientes.get(position);
            //holder.binding.idHolder.setText("@string/"+String.valueOf(paciente.getId()));
            holder.binding.nameHolder.append(": "+paciente.getNombre());
            holder.binding.surnameHolder.append(": "+paciente.getApellidos());
            holder.binding.medicineHolder.append(": "+paciente.getMedicamento());
           // pacienteViewModel.setLoggedInPacienteID(String.valueOf(paciente.getId()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditPacientes.pacienteID = paciente.getId();
                    navController.navigate(R.id.action_show_to_EditPacientes);
                }
            });
        }

        @Override
        public int getItemCount() {
            return pacientes != null ? pacientes.size() : 0;
        }

        public void establishList(List<Paciente> pacientes){
            this.pacientes = pacientes;
            notifyDataSetChanged();
        }
    }
}
