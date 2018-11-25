package br.com.caelum.livraria.modelo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.caelum.livraria.dao.DAO;

public class LivroDataModel extends LazyDataModel<Livro> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2948232237230849780L;
	
	DAO<Livro> dao = new DAO<Livro>(Livro.class);
	
	public LivroDataModel() {
	    super.setRowCount(dao.quantidadeDeElementos());
	}
	
	@Override
	public List<Livro> load(int inicio, int quantidade, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object> filters) {
        Map<String, String> filtersMap = null;
        
        Set<String> keySet = filters.keySet();
        
        boolean filterExists = keySet != null && !keySet.isEmpty();
        
        if(filterExists) {
        	filtersMap = new LinkedHashMap<String, String>();
        	
            for (String key : keySet) {
                if (filters.containsKey(key)) {
                    filtersMap.put(key, (String) filters.get(key));
                }
            }
        	
        }

	    List<Livro> lista = dao.listaTodosPaginada(inicio, quantidade, filtersMap);
	    
	    setRowCount(dao.quantidadeDeElementos(filtersMap));
	    
		return lista;
	}

}
