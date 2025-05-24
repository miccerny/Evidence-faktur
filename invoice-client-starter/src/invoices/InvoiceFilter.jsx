
import InputSelect from "../components/InputSelect";
import InputField from "../components/InputField";

const InvoiceFilter = (props) => {
    // Funkce volaná při změně hodnoty v některém z inputů/selectů
    const handleChange = (e) => {
        props.handleChange(e);
    }
    // Funkce volaná při odeslání formuláře
    const handleSubmit = (e) => {
        props.handleSubmit(e);
    }
    // Extrahování filtru z props pro snazší použití
    const filter = props.filter;

    return (
        <form onSubmit={handleSubmit}>
            <div className="row">
                {/* Výběr dodavatele */}
                <div className="col">
                    <InputSelect
                        name="sellerID"
                        items={props.sellerList}
                        handleChange={handleChange}
                        label="Dodavatel"
                        prompt="nevybrán"
                        value={filter.sellerID}
                    />
                </div>
                {/* Výběr odběratele */}
                <div className="col">
                    <InputSelect
                        name="buyerID"
                        items={props.buyerList}
                        handleChange={handleChange}
                        label="Odběratel"
                        prompt="nevybrán"
                        value={filter.buyerID}
                    />
                </div>
                {/* Textové pole pro zadání části názvu produktu */}
                <div className="col">
                    <InputField
                        type="text"
                        name="product"
                        handleChange={handleChange}
                        label="Produkt"
                        prompt="část názvu"
                        value={filter.product || ""}
                    />
                </div>
            </div>
            <div className="row">
                {/* Minimální cena */}
                <div className="col">
                    <InputField
                        type="number"
                        name="minPrice"
                        min="0"
                        handleChange={handleChange}
                        label="Od částky"
                        prompt="(neuvedena)"
                        value={filter.minPrice ? filter.minPrice : ''}
                    />
                </div>
                {/* Maximální cena */}
                <div className="col">
                    <InputField
                        type="number"
                        min="0"
                        name="maxPrice"
                        handleChange={handleChange}
                        label="Do částky"
                        prompt="(neuvedená)"
                        value={filter.maxPrice ? filter.maxPrice : ''}
                    />
                </div>
                {/* Limit počtu faktur k zobrazení */}
                <div className="col">
                    <InputField
                        type="number"
                        min="1"
                        name="limit"
                        handleChange={handleChange}
                        label="Limit počtu faktur"
                        prompt="(neuveden)"
                        value={filter.limit ? filter.limit : ''}
                    />
                </div>
            </div>
            {/* Tlačítko pro odeslání formuláře */}
            <div className="row mt-2 rounded-1">
                <div className="col d-flex justify-content-center">
                    <input
                        type="submit"
                        className="btn btn-info float-right mt-2"
                        value={props.confirm}
                    />
                </div>
            </div>
        </form>
    );
};
export default InvoiceFilter;