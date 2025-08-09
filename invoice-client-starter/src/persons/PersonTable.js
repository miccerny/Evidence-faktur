import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import '../styles/CardStyles.css'
import '../styles/ButtonStyles.css'
import { useSession } from "../contexts/session";

const ITEMS_PER_PAGE = 5;

const PersonTable = ({ items, deletePerson, page, setPage, totalPages, totalElements, openCardId, toggleCard }) => {
    const { session } = useSession();
    const authorities = session.data?.authorities || [];
    const isAdmin = authorities.includes("ROLE_ADMIN");
    const isUser = authorities.includes("ROLE_USER");
    const userEmail = session.data?.email;

    console.log("Render PersonTable", session.status);
    console.log("Aktuální stránka (page):", page, typeof page);
    console.log("Session:", session);
    
    if (session.status === "loading") {
        return <div>Načítání dat…</div>;
    }

    return (
        <>

            <div className="container">
                <p>
                    Počet osob: {totalElements}
                </p>
                <div className="row g-3">
                    {items.map((person, index) => (
                        <div className="col-md-6 col-lg-12" key={person._id}>
                            <div className="custom-card shadow-sm h-100">
                                <div className="card-body position-relative">
                                    <div className="d-flex justify-content-between align-items-center m-1"
                                        onClick={() => toggleCard(person._id)}
                                    >
                                        <h5 className="custom-card-title">
                                            {(page * ITEMS_PER_PAGE) + index + 1} - {person.name}
                                        </h5>
                                        <button className="btn btn-sm btn-outline-primary">
                                            {openCardId === person._id ? "X" : "Akce"}
                                        </button>
                                    </div>
                                    <div className={`action-panel mt-3 ${openCardId === person._id ? "open" : ""}`}>
                                        <div className="d-grid gap-2">
                                            <Link
                                                to={"/persons/show/" + person._id}
                                                className="button button-detail button-detail:hover"
                                            >
                                                Zobrazit
                                            </Link>
                                            {(isAdmin || person.ownerEmail === userEmail) && person.ownerEmail && (
                                                <Link
                                                    to={"/persons/edit/" + person._id}
                                                    className="button button-edit button-edit:hover"
                                                >
                                                    Upravit
                                                </Link>
                                            )}
                                            {isAdmin ? (
                                                <button
                                                    onClick={() =>
                                                        deletePerson(person._id)
                                                    }
                                                    className="button button-delete button-delete:hover"
                                                >
                                                    Odstranit
                                                </button>
                                            ) : null}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
                <div className="d-flex justify-content-center align-items-center mt-4">
                    <div>
                        <button
                            className="btn btn-outline-secondary me-2"
                            disabled={page === 0}
                            onClick={() => setPage(page - 1)}
                        >
                            Předchozí
                        </button>
                        <button
                            className="btn btn-outline-secondary"
                            disabled={page + 1 >= totalPages}
                            onClick={() => setPage(page + 1)}
                        >
                            Další
                        </button>
                        <span className="ms-3">Stránka {page + 1} z {totalPages}</span>
                    </div>
                </div>
                <div className="mt-3">
                    {isUser ? (
                        <Link to={"/persons/create"} className="btn btn-success">
                            Nová osoba
                        </Link>
                    ) : null}
                </div>
            </div>
        </>
    );
};
export default PersonTable;
