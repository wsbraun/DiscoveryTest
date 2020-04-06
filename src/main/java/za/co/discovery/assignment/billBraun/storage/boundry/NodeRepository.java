package za.co.discovery.assignment.billBraun.storage.boundry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.co.discovery.assignment.billBraun.storage.entity.Node;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long>  {
	
	Optional<Node> findBySymbol(String symbol);
	
}
