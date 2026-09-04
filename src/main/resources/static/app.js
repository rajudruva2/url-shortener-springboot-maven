async function loadUrls() {
    const response = await fetch('/api/urls');
    const urls = await response.json();

    document.getElementById('totalLinks').textContent = urls.length;
    document.getElementById('totalClicks').textContent =
        urls.reduce((sum, item) => sum + item.clicks, 0);
    document.getElementById('activeLinks').textContent =
        urls.filter(item => item.active).length;

    const table = document.getElementById('urlTable');
    table.innerHTML = urls.map(item => `
        <tr>
            <td><strong>${item.code}</strong></td>
            <td class="url" title="${escapeHtml(item.originalUrl)}">${escapeHtml(item.originalUrl)}</td>
            <td>${item.clicks}</td>
            <td><span class="badge ${item.active ? '' : 'off'}">${item.active ? 'Active' : 'Inactive'}</span></td>
            <td class="actions">
                <button onclick="copyUrl('${item.code}')">Copy</button>
                <button class="secondary" onclick="showAnalytics('${item.code}')">Analytics</button>
            </td>
        </tr>
    `).join('');
}

async function createUrl(event) {
    event.preventDefault();

    const originalUrl = document.getElementById('urlInput').value;
    const result = document.getElementById('result');

    const response = await fetch('/api/urls', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ originalUrl })
    });

    const data = await response.json();

    if (!response.ok) {
        result.textContent = data.detail || data.message || 'Unable to create short URL';
        return;
    }

    result.innerHTML =
        `Created: <a href="/r/${data.code}" target="_blank">${location.origin}/r/${data.code}</a>`;
    document.getElementById('urlInput').value = '';
    loadUrls();
}

async function copyUrl(code) {
    await navigator.clipboard.writeText(`${location.origin}/r/${code}`);
    alert('Short URL copied.');
}

async function showAnalytics(code) {
    const response = await fetch(`/api/urls/${code}/analytics`);
    const data = await response.json();

    alert(
        `Code: ${data.code}\n` +
        `Clicks: ${data.clicks}\n` +
        `Status: ${data.active ? 'Active' : 'Inactive'}\n` +
        `Original URL: ${data.originalUrl}`
    );
}

function escapeHtml(value) {
    return value.replace(/[&<>"']/g, char => ({
        '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#039;'
    }[char]));
}

document.getElementById('urlForm').addEventListener('submit', createUrl);
loadUrls();
