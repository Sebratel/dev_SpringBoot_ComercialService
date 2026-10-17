package br.com.sebratel.comercial.repository.erp;

import br.com.sebratel.comercial.model.ErpContract;
import br.com.sebratel.comercial.repository.erp.projections.AquisicaoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AquisicaoRepository extends JpaRepository<ErpContract, Long> {

    @Query(value = """
            
                SELECT
                    par.id,
                    sp.code AS "codigo",
                    sp.title AS "produto",
                    par.date AS "data",
                    p.name AS "requisitadoPor",
                    par.receipt_date_prevision AS "dataPrevisao",
                    pari.units AS "outrosStatus",
                        CASE\s
                            WHEN UPPER(par.observation) LIKE '%SCHARLAU%' THEN 'BASE OPERACIONAL - SÃO LEOPOLDO'
                            WHEN UPPER(par.observation) LIKE '%VICTOR BARRETO%' THEN 'BASE OPERACIONAL - CANOAS'
                            WHEN UPPER(par.observation) LIKE '%NAVEGANTES%' THEN 'SEDE - PORTO ALEGRE'
                        END AS "BASE",
                        CASE\s
                            WHEN par.status = 4 THEN 'Aguardando Entrega'
                            WHEN par.status = 2 THEN 'Em Aprovação'
                            WHEN par.status = 1 THEN 'Inclusão'
                        END AS "status"
                FROM product_acquisition_requests par
                LEFT JOIN product_acquisition_request_items AS pari ON pari.product_acquisition_request_id = par.id
                LEFT JOIN service_products sp ON sp.id = pari.service_product_id
                LEFT JOIN people p ON p.id = par.person_id
                LEFT JOIN company_place_business_units cpbu ON cpbu.company_place_id = par.company_place_id\s
                LEFT JOIN units_measures um ON um.code = sp.code
                WHERE par.status in (1, 2, 4)\s
                AND par.deleted IS FALSE
                GROUP BY par.id, sp.code, sp.title, par.date, p.name, par.receipt_date_prevision, pari.units
                ORDER BY par.date desc
            """, nativeQuery = true)
    List<AquisicaoProjection> findAquisicoesPendentes();
}