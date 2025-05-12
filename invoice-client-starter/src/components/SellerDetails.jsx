
function SellerDetails({seller}){
    return(
        <div className="seller-details">
                 <p>
                    <strong>DIČ:</strong>
                    <br />
                    {seller.taxNumber}
                </p>
                <p>
                    <strong>Bankovní účet:</strong>
                    <br />
                    {seller.accountNumber}/{seller.bankCode} 
                </p>
                <p>
                    <strong>IBAN:</strong><br/>
                    ({seller.iban})
                </p>
                <p>
                    <strong>Tel.:</strong>
                    <br />
                    {seller.telephone}
                </p>
                <p>
                    <strong>Mail:</strong>
                    <br />
                    {seller.mail}
                </p>
                <p>
                    <strong>Sídlo:</strong>
                    <br />
                    {seller.street}, {seller.city} <br/>
                    {seller.zip}, {seller.country}
                </p>
                <p>
                    <strong>Poznámka:</strong>
                    <br />
                    {seller.note}
                </p>
                </div>
    );
};
export default SellerDetails;