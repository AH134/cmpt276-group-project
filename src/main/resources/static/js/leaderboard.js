function showTable(type) {
    document.getElementById('growerTable').style.display = 'none';
    document.getElementById('sponsorTable').style.display = 'none';
    
    if (type === 'grower') {
        document.getElementById('growerTable').style.display = 'table';
    } else if (type === 'sponsor') {
        document.getElementById('sponsorTable').style.display = 'table';
    }
}