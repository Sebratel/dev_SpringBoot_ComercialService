package br.com.sebratel.comercial.repository.erp;

import br.com.sebratel.comercial.model.ErpContract;
import br.com.sebratel.comercial.repository.erp.projections.ContractFirstPaymentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractPaymentRepository extends JpaRepository<ErpContract, Long> {

    @Query(value = "WITH PERSONALIZADO AS ( " +
            "    SELECT  " +
            "        p.name AS NOME,  " +
            "        co.contract_number AS NUMERO_CONTRATO, " +
            "        MIN(fr.issue_date) AS PRIMEIRA_EMISSAO, " +
            "        x.client_paid_date AS PAGAMENTO_CLIENTE, " +
            "        co.description AS DESCRIPTION, " +
            "        co.contract_number AS CONTRACTNUMBER, " +
            "        co.v_status AS STATUS, " +
            "        co.created AS DATA_CRIACAO, " +
            "        ROW_NUMBER() OVER (PARTITION BY p.name, co.contract_number ORDER BY x.client_paid_date ASC) AS ORDENADO " +
            "    FROM erp.financial_receipt_titles x " +
            "    LEFT JOIN erp.people p ON p.id = x.client_id " +
            "    LEFT JOIN erp.financial_receivable_titles fr ON x.financial_receivable_title_id = fr.id " +
            "    LEFT JOIN erp.contracts co ON co.id = fr.contract_id " +
            "    LEFT JOIN erp.authentication_contracts ac ON ac.contract_id = co.id " +
            "    LEFT JOIN erp.service_products sp ON ac.service_product_id = sp.id " +
            "    LEFT JOIN bank_accounts b ON x.bank_account_id = b.id " +
            "    WHERE co.contract_number IS NOT NULL " +
            "    AND b.description <> 'Conta Transitória (Baixa/Perda) - YES' " +
            "    GROUP BY p.name, fr.issue_date, co.description, co.contract_number, co.v_status, co.created, sp.title, x.client_paid_date " +
            ") " +
            "SELECT  " +
            "    NOME, " +
            "    NUMERO_CONTRATO, " +
            "    PRIMEIRA_EMISSAO, " +
            "    PAGAMENTO_CLIENTE, " +
            "    DATA_CRIACAO, " +
            "    CONTRACTNUMBER, " +
            "    DESCRIPTION, " +
            "    STATUS " +
            "FROM PERSONALIZADO " +
            "WHERE ORDENADO <= 1 " +
            "ORDER BY NOME ASC", nativeQuery = true)
    List<ContractFirstPaymentProjection> findFirstContractPayments();




}