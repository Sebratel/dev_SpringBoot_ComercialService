package br.com.sebratel.comercial.repository.erp;

import br.com.sebratel.comercial.model.ErpContract;
import br.com.sebratel.comercial.repository.erp.projections.ContratoSemFaturaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoSemFaturaRepository extends JpaRepository<ErpContract, Long> {

    @Query(value = """
        SELECT 
            c.description AS contractDescription,
            ac.user AS pppoe,
            COUNT(frt.title) AS qtdFaturas
        FROM erp.authentication_contracts ac
        LEFT JOIN erp.contracts c ON c.id = ac.contract_id 
        LEFT JOIN erp.financial_receivable_titles frt ON frt.contract_id = c.id
        WHERE ac.created < CURRENT_DATE - INTERVAL '30 days'
          AND c.contract_type_id NOT IN (3, 10, 23)
        GROUP BY c.description, ac.user
        HAVING COUNT(frt.title) = 0
        """, nativeQuery = true)
    List<ContratoSemFaturaProjection> findContratosSemFatura();
}